package pa.rest.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AccessLogCount {

	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date dateTime;

	private int count;

	public AccessLogCount(Date dateTime, int count) {
		this.dateTime = dateTime;
		this.count = count;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
