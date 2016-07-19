
package pa.domain.statement;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlPath;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Payment extends AbstractPersistable<Long> {

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	private static final long serialVersionUID = 8312764342778073073L;

	@XmlPath("s:NtryDtls/s:TxDtls/s:Refs/s:TxId/text()")
	@Column(unique = true)
	private Integer transactionId;
	
	@XmlElement(name="CdtDbtInd")
	private String direction;
	
	@XmlElement(name="Amt")
	private Double amount;
		
	@XmlPath("s:NtryDtls/s:TxDtls/s:RltdPties/s:DbtrAcct/s:Id/s:IBAN/text()")
	private String debitAccount;
	
	@XmlPath("s:NtryDtls/s:TxDtls/s:RltdPties/s:CdtrAcct/s:Id/s:IBAN/text()")
	private String creditAccount;
	
	@XmlPath("s:NtryDtls/s:TxDtls/s:RltdPties/s:Dbtr/s:Nm/text()")
	private String debitor;
	
	@XmlPath("s:NtryDtls/s:TxDtls/s:RltdPties/s:Cdtr/s:Nm/text()")
	private String creditor;
	
	@XmlPath("s:NtryDtls/s:TxDtls/s:RmtInf/s:Ustrd/text()")
	private String description;
	
	@XmlPath("s:BookgDt/s:Dt/text()")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date date;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}

	public String getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}

	public String getDebitor() {
		return debitor;
	}

	public void setDebitor(String debitor) {
		this.debitor = debitor;
	}

	public String getCreditor() {
		return creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
