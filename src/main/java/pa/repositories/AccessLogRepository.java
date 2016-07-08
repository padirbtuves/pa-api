package pa.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pa.domain.AccessLog;

@Repository
public interface AccessLogRepository extends CrudRepository<AccessLog, Long> {

	List<AccessLog> findFirst10ByOrderByDateTimeDesc();
}
