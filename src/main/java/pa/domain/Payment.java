package pa.domain;

import java.util.Date;

import org.springframework.data.jpa.domain.AbstractPersistable;

public class Payment extends AbstractPersistable<Long> {

	private Long amount;
	
	private String debitAccount;
	
	private String creditAccount;
	
	private String debitor;
	
	private String creditor;
	
	private String description;
	
	private Date date;
	
}
