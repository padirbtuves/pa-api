package pa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pa.domain.statement.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

	Payment findOneByTransactionId(Integer transactionId);
}
