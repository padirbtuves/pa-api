package pa.controlers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Reject;
import com.twilio.twiml.voice.Reject.Reason;

import pa.domain.LockResponse;
import pa.domain.UserAccount;
import pa.services.LockService;
import pa.services.UserService;

@Controller
@RequestMapping("/hook")
public class WebhooksController {

	private static final Logger LOG = Logger.getLogger(WebhooksController.class.getName());

	@Autowired
	private UserService userService;

	@Autowired
	private LockService lockService;

	@PostMapping(path = "twillio", produces = { "application/xml", "text/xml" })
	public ResponseEntity<String> twillioIncommingCall(@RequestParam(name = "From", required = true) String from) {
		LOG.info("Incomming call from " + from);
		Reason reason = Reason.REJECTED;
		UserAccount userAccount = userService.getUserAccount(from);
		if (userAccount != null && userAccount.isValid()) {
			lockService.unlock();
			reason = Reason.BUSY;
		}
		LOG.info("Sending response with reason " + reason);

		Reject reject = new Reject.Builder().reason(reason).build();
		VoiceResponse response = new VoiceResponse.Builder().reject(reject).build();
		return new ResponseEntity<String>(response.toXml(), HttpStatus.OK);
	}
	
	@GetMapping(path= "lock")
	public @ResponseBody LockResponse getLockStatus() {
		LockResponse response = new LockResponse();
		response.setLocked(lockService.isLocked());

		return response;
	}
}
