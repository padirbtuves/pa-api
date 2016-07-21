package pa.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pa.domain.AccessLog;
import pa.domain.UserAccount;
import pa.rest.data.AccessLogCount;
import pa.rest.data.AuthenticateTagResult;
import pa.rest.data.Finances;
import pa.services.AccessLogService;
import pa.services.AdminService;
import pa.services.UserService;

@RestController
public class PaController {

	@Autowired
	private UserService userService;

	@Autowired
	private AccessLogService logService;

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "/app/user/all")
	public List<UserAccount> listAll() {
		return userService.getAll();
	}

	@RequestMapping("/app/user/current")
	public UserAccount getCurrentUser() {
		return userService.getUserAccount();
	}

	@RequestMapping(value = "/app/user/update", method = RequestMethod.POST)
	public UserAccount updateUser(@RequestBody UserAccount userAccount) {
		return userService.save(userAccount);
	}

	@RequestMapping("/auth/log")
	public List<AccessLog> accessLog() {
		return logService.getLastAccessLog();
	}

	@RequestMapping("/stats/hourlyLogs")
	public List<AccessLogCount> getHourlyLogs() {
		DateTime till = DateTime.now();
		return logService.getEventCount(till.minusWeeks(1).toDate(), till.toDate(), "door 0", AccessLogService.LOG_INTERVAL.HOUR);
		//return logService.getHourlyLogs(till.minusWeeks(1).toDate(), till.toDate());
	}

	@RequestMapping("/stats/dailyLogs")
	public List<AccessLogCount> getDailyLogs() {
		DateTime till = DateTime.now();
		return logService.getDailyLogs(till.minusWeeks(4).toDate(), till.toDate());
	}

	@RequestMapping("/stats/finances")
	public Finances getFinances() {
		Finances result = new Finances();
		DateTime till = DateTime.now();
		result.setPayments(adminService.getPayments(till.minusMonths(6).toDate(), till.toDate()));
		result.setInitialAmount(adminService.getAmountOn(till.minusMonths(6).toDate()));

		return result;
	}

	@RequestMapping("/auth/nfc")
	public AuthenticateTagResult authentcateTag(@RequestParam(name = "id") String tagId) {
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

	@RequestMapping("/event")
	public List<String> logEvent(String clientId, String eventName) {
		System.out.println(clientId + " " + eventName);
		// TODO check client id
		adminService.logEvent(eventName);

		return new ArrayList<>();
	}
}
