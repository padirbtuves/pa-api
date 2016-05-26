/**
 * 
 */
package pa.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Lists;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import pa.domain.UserAccount;
import pa.repositories.UserAccountRepository;

/**
 * @author vincentas
 *
 */
@Service
public class UserService {

	@Autowired
	private UserAccountRepository accountRepository;
	
	public synchronized UserAccount getUserAccount() {
		UserAccount result = null;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication oa = (OAuth2Authentication) authentication;
			String googleId = (String) oa.getUserAuthentication().getPrincipal();

			result = accountRepository.findOneByGoogleId(googleId);
			if (result == null) {
				result = makeGoogleUserAccount(oa);
				accountRepository.save(result);
			}
			// update user info from latest data
		}
		
		return result;
	}
	
	public UserAccount save(UserAccount userAccount) {
		UserAccount currentUser = getUserAccount();
		if (currentUser.getId().equals(userAccount.getId()) || currentUser.isAdmin()) {
			return accountRepository.save(userAccount);
		} else {
			return userAccount;
		}
	}
	
	public UserAccount getUserAccount(String tagId) {
		return accountRepository.findOneByTagId(tagId);
	}
	
	public List<UserAccount> getAll() {
		if (getUserAccount().isAdmin()) {
			return Lists.newArrayList(accountRepository.findAll());
		} else {
			return new ArrayList<UserAccount>();
		}
	}
	
	private UserAccount makeGoogleUserAccount(OAuth2Authentication authentication) {
		UserAccount result = new UserAccount();
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
		Userinfoplus userInfo = getUserInfo(details.getTokenValue());
		
		
		result.setGoogleId((String) authentication.getPrincipal());
		result.setAdmin(false);
		result.setEmail(userInfo.getEmail());
		result.setDisplayName(userInfo.getName());
		result.setPhone(null);
		result.setTagId(null);
		result.setValidTill(null);
		
		return result;

	}
	
	private Userinfoplus getUserInfo(String token) {
		Userinfoplus result = null;

		GoogleCredential credential = new GoogleCredential().setAccessToken(token);
		Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
				.setApplicationName("Oauth2").build();
		try {
			result = oauth2.userinfo().get().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}
