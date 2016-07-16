package pa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonIgnoreProperties(value = {"new", "googleId"})
@JsonInclude(Include.NON_NULL)
public class UserAccount extends AbstractPersistable<Long> {

	@Column(unique = true)
	private String googleId;

	@Column(unique = true)
	private String email;

	@Column(unique = true, nullable = true)
	private String tagId;

	@Column(unique = true, nullable = true)
	private String phone;
	
	private String displayName;

	private boolean admin = false;

	private Date validTill;

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isAdmin() {
		return admin || "vincnetas@gmail.com".equalsIgnoreCase(getEmail());
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Date getValidTill() {
		return validTill;
	}

	public void setValidTill(Date validTill) {
		this.validTill = validTill;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public boolean isValid() {
		return validTill != null && validTill.after(new Date());
	}

	
}
