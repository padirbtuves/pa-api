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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pa.domain.AccessLogCount;
import pa.services.AccessLogService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private AccessLogService logService;

	@RequestMapping
	public String user(HttpServletRequest request) {
		return "user/home";
	}

	@RequestMapping(path = "list")
	public String list(HttpServletRequest request) {
		return "user/list";
	}

	@RequestMapping(path="log")
	public String log(HttpServletRequest request) {
		
		return "user/log";
	}

}
