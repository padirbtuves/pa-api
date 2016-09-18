/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package pa.controlers;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pa.domain.statement.Statement;
import pa.domain.statement.StatementDocument;
import pa.services.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private Unmarshaller unmarshaller;
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping
	public String home() throws IOException {
		return "admin/home";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/upload/statement")
	public String handleStatementUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		if (!file.isEmpty()) {
			try {
				StatementDocument document = (StatementDocument) unmarshaller.unmarshal(file.getInputStream());
			    Statement statemetn = document.getStatementRoot().getStatement();
				
			    adminService.storePaymentData(statemetn.getPayments());
			} catch (JAXBException | IOException | RuntimeException e) {
				redirectAttributes.addFlashAttribute("message", "Failued to upload " + file.getOriginalFilename() + " => " + e.getMessage());
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
		}

		return "redirect:/admin";
	}
}
