/**
 * 
 */
package pa.services;

import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pa.domain.statement.Payment;
import pa.repositories.PaymentRepository;

/**
 * @author vincentas
 *
 */
@Service
public class AdminService {
	
	private static final Logger LOG = Logger.getLogger(AdminService.class.getName());

	@Autowired
	private PaymentRepository paymentRepository;

	@Transactional
	public void storePaymentData(List<Payment> payments) {
		int count = 0;
		
		for (Payment payment : payments) {
			LOG.info("Payment : " + payment.getTransactionId());
			if (paymentRepository.findOneByTransactionId(payment.getTransactionId()) == null) {
				paymentRepository.save(payment);
				count ++;
			}
		}
		
		LOG.info("Imported payments : " + count);
	}
}
