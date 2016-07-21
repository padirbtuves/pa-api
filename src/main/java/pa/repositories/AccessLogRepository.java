package pa.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pa.domain.AccessLog;

@Repository
public interface AccessLogRepository extends CrudRepository<AccessLog, Long> {

	List<AccessLog> findFirst10ByOrderByDateTimeDesc();

	@Query(value="SELECT date_trunc('day', date_time) dateTime, CAST(count(id) AS INT) FROM access_log WHERE date_time >= ?1 AND date_time <= ?2 GROUP BY dateTime ORDER BY dateTime", nativeQuery=true)
	List<Object[]> findDailyLog(Date from, Date till);
	
	@Query(value="SELECT date_trunc('hour', date_time) dateTime, CAST(count(id) AS INT) FROM access_log WHERE date_time >= ?1 AND date_time <= ?2 GROUP BY dateTime ORDER BY dateTime", nativeQuery=true)
	List<Object[]> findHourlyLog(Date from, Date till);

	@Query(value="SELECT date_trunc('day', date) dateTime, CAST(count(id) AS INT) FROM event_log WHERE date >= ?1 AND date <= ?2 AND event_name = ?3 GROUP BY dateTime ORDER BY dateTime", nativeQuery=true)
	List<Object[]> findDailyEventLog(Date from, Date till, String eventName);
	
	@Query(value="SELECT date_trunc('hour', date) dateTime, CAST(count(id) AS INT) FROM event_log WHERE date >= ?1 AND date <= ?2 AND event_name = ?3 GROUP BY dateTime ORDER BY dateTime", nativeQuery=true)
	List<Object[]> findHourlyEventLog(Date from, Date till, String eventName);
}
