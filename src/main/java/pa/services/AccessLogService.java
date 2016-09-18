/**
 * 
 */
package pa.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pa.domain.AccessLog;
import pa.repositories.AccessLogRepository;
import pa.rest.data.AccessLogCount;

/**
 * @author vincentas
 *
 */
@Service
public class AccessLogService {

	public enum LOG_INTERVAL {DAY, HOUR};
	
	@Autowired
	private AccessLogRepository accessLogRepository;

	public void createAccessLog(AccessLog accessLog) {
		accessLogRepository.save(accessLog);
	}

	public List<AccessLog> getLastAccessLog() {
		return accessLogRepository.findFirst10ByOrderByDateTimeDesc();
	}
	
	public List<AccessLogCount> getEventCount(Date from, Date till, String eventName, LOG_INTERVAL interval) {
		List<Object[]> data;
		if (interval.equals(LOG_INTERVAL.DAY)) {
			data = accessLogRepository.findDailyEventLog(from, till, eventName);
		} else if (interval.equals(LOG_INTERVAL.HOUR)) {
			data = accessLogRepository.findHourlyEventLog(from, till, eventName);
		} else {
			data = new ArrayList<>();
		}
		
		return convert(data);
	}
	
	private List<AccessLogCount> convert(List<Object[]> data) {
		List<AccessLogCount> result = new ArrayList<>();
		for (Object[] item : data) {
			result.add(new AccessLogCount((Date) item[0], (int) item[1]));
		}
		
		return result;
	}

	public List<AccessLogCount> getDailyLogs(Date from, Date till) {
		return convert(accessLogRepository.findDailyLog(from, till));
	}
	
	public List<AccessLogCount> getHourlyLogs(Date from, Date till) {
		return convert(accessLogRepository.findHourlyLog(from, till));
	}
}
