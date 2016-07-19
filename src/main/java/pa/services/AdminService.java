/**
 * 
 */
package pa.services;

import java.util.List;

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

	@Autowired
	private PaymentRepository paymentRepository;

	@Transactional
	public void storePaymentData(List<Payment> payments) {
		int count = 0;
		
		for (Payment payment : payments) {
			if (paymentRepository.findOneByTransactionId(payment.getTransactionId()) == null) {
				paymentRepository.save(payment);
				count ++;
			}
		}
		
		System.out.println("Imported payments : " + count);
	}
}
