/**
 * 
 */
package pa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * @author vincentas
 *
 */
@Entity
public class AccessLog extends AbstractPersistable<Long>{

	@Column(nullable=false)
	private Date dateTime;
	
	@ManyToOne(optional=false)
	private UserAccount user;
	
	public AccessLog() {
		
	}
	
	public AccessLog(UserAccount user) {
		this.user = user;
		this.dateTime = new Date();
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}
	
	
}
