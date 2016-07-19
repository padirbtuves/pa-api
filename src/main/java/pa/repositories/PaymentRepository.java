package pa.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pa.domain.statement.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

	Payment findOneByTransactionId(Integer transactionId);
	
	List<Payment> findByDateBetweenOrderByDate(Date from, Date till);

	@Query("SELECT sum(amount) FROM Payment WHERE date < ?1 AND direction = 'DBIT'")
	Double getDebitTill(Date date);

	@Query("SELECT sum(amount) FROM Payment WHERE date < ?1 AND direction = 'CRDT'")
	Double getCreditTill(Date date);
}
