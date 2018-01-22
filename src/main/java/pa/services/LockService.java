/**
 * 
 */
package pa.services;

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;

/**
 * @author vincentas
 *
 */
@Service
public class LockService {

	private Instant unlocked = Instant.MIN;
	
	public void unlock() {
		unlocked = Instant.now();
	}
	
	public boolean isLocked() {
		return Instant.now().minus(Duration.ofSeconds(5)).isAfter(unlocked);
	}
}
