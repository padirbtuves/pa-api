package pa.domain;

import java.util.Date;

import javax.persistence.Entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class EventLog extends AbstractPersistable<Long> {

	private Date date;

	private String eventName;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public EventLog() {

	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public EventLog(String name) {
		this.date = new Date();
		this.eventName = name;
	}
}
