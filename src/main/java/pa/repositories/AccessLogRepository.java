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

	@Query(value="SELECT to_date(d.date, 'YYYY-MM-DD') AS dateTime, CAST(count(al.id) AS INT) AS total FROM (" +
			"SELECT to_char(date_trunc('day', (current_date - offs)), 'YYYY-MM-DD') " +
			"AS date FROM generate_series(0, 365, 1) AS offs) d " + 
			"LEFT OUTER JOIN access_log al ON (d.date=to_char(date_trunc('day', al.date_time), 'YYYY-MM-DD')) " +
			"WHERE al.date_time >= ?1 AND al.date_time <= ?2 GROUP BY d.date ORDER BY d.date", nativeQuery=true)
	List<Object[]> findDailyLog(Date from, Date till);
	
	@Query(value="SELECT date_trunc('hour', date_time) dateTime, CAST(count(id) AS INT) FROM access_log WHERE date_time >= ?1 AND date_time <= ?2 GROUP BY dateTime ORDER BY dateTime", nativeQuery=true)
	List<Object[]> findHourlyLog(Date from, Date till);
}
