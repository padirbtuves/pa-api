package pa.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pa.domain.AccessLog;
import pa.domain.UserAccount;
import pa.rest.data.AuthenticateTagResult;
import pa.services.AccessLogService;
import pa.services.UserService;

@RestController
public class PaController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AccessLogService logService;
	
	@RequestMapping(value="/app/user/all")
	public List<UserAccount> listAll() {
		return userService.getAll();
	}

	@RequestMapping("/app/user/current")
	public UserAccount getCurrentUser() {
		return userService.getUserAccount();
	}

	@RequestMapping(value="/app/user/update", method=RequestMethod.POST)
	public UserAccount updateUser(@RequestBody UserAccount userAccount) {
		return userService.save(userAccount);
	}

	@RequestMapping("/auth/nfc")
	public AuthenticateTagResult authentcateTag(@RequestParam(name="id") String tagId) {
		UserAccount userAccount = userService.getUserAccount(tagId);
		
		Date validTill = null;
		if (userAccount != null) {
			validTill = userAccount.getValidTill();
		} 
		
		AuthenticateTagResult result = new AuthenticateTagResult();
		result.setId(tagId);
		result.setValid(validTill != null && validTill.after(new Date()));
		result.setValidTill(validTill);
		
		if (result.isValid()) {
			logService.createAccessLog(new AccessLog(userAccount));
		}
		
		return result;
	}

}
