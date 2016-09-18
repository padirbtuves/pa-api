package pa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pa.domain.EventLog;

@Repository
public interface EventLogRepository extends CrudRepository<EventLog, Long> {

}
