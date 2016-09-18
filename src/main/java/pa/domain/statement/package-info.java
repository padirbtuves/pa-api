@XmlSchema(
        namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02",
        attributeFormDefault = XmlNsForm.QUALIFIED,
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = {
    @XmlNs(
            prefix = "s",
            namespaceURI = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.02")
})
package pa.domain.statement;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;