/**
 * 
 */
package pa.utils;

import java.util.Collection;

import pa.domain.Payment;

/**
 * @author vincentas
 *
 */
public interface PaymentParser {

	Collection<Payment> parse();
}
