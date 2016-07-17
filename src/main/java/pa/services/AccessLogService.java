/**
 * 
 */
package pa.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.Instant;
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

	@Autowired
	private AccessLogRepository accessLogRepository;

	public void createAccessLog(AccessLog accessLog) {
		accessLogRepository.save(accessLog);
	}

	public List<AccessLog> getLastAccessLog() {
		return accessLogRepository.findFirst10ByOrderByDateTimeDesc();
	}

	public List<AccessLogCount> getDailyLogs(Date from, Date till) {
		SortedMap<DateTime, AccessLogCount> data = new TreeMap<>();
		for (Object[] item : accessLogRepository.findDailyLog(from, till)) {
			data.put(new DateTime((Date) item[0]), new AccessLogCount((Date) item[0], (int) item[1]));
			System.out.println(item[0]);
		}

		List<AccessLogCount> result = new ArrayList<>();
		DateTime first = new DateTime(from).dayOfYear().roundFloorCopy();
		while (first.isBefore(new Instant(till))) {
			AccessLogCount item = data.get(first);
			if (item == null) {
				item = new AccessLogCount(first.toDate(), 0);
			}
			
			result.add(item);
			first = first.plusDays(1);
		}

		return result;
	}
	
	public List<AccessLogCount> getHourlyLogs(Date from, Date till) {
		SortedMap<DateTime, AccessLogCount> data = new TreeMap<>();
		for (Object[] item : accessLogRepository.findHourlyLog(from, till)) {
			data.put(new DateTime((Date) item[0]), new AccessLogCount((Date) item[0], (int) item[1]));
			System.out.println(item[0]);
		}

		List<AccessLogCount> result = new ArrayList<>();
		DateTime first = new DateTime(from).hourOfDay().roundFloorCopy();
		while (first.isBefore(new Instant(till))) {
			AccessLogCount item = data.get(first);
			if (item == null) {
				item = new AccessLogCount(first.toDate(), 0);
			}
			
			result.add(item);
			first = first.plusHours(1);
		}

		return result;
	}
}
