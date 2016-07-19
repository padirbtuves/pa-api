package pa.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pa.domain.statement.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

	Payment findOneByTransactionId(Integer transactionId);
	
	List<Payment> findByDateBetweenOrderByDate(Date from, Date till);
}
