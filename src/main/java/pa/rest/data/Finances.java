package pa.rest.data;

import java.util.List;

import pa.domain.statement.Payment;

public class Finances {
	
	private Double initialAmount;
	
	private List<Payment> payments;

	public Double getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(Double initialAmount) {
		this.initialAmount = initialAmount;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	
	

}
