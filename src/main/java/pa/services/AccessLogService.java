/**
 * 
 */
package pa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pa.domain.AccessLog;
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
}
