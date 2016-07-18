package pa.domain.statement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Document")
@XmlType(namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatementDocument {

	@XmlElement(name = "BkToCstmrStmt")
	private StatementRoot statementRoot;

	public StatementRoot getStatementRoot() {
		return statementRoot;
	}

	public void setStatementRoot(StatementRoot statementRoot) {
		this.statementRoot = statementRoot;
	}

}
