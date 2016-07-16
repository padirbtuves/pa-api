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
import pa.domain.AccessLogCount;
import pa.repositories.AccessLogRepository;

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

	public List<AccessLogCount> getLogs(Date from, Date till) {
		List<AccessLogCount> result = new ArrayList<AccessLogCount>();
		for (Object[] data : accessLogRepository.findDailyLog(from, till)) {
			result.add(new AccessLogCount((Date) data[0], (int) data[1]));
		}

		return result;
	}
}
