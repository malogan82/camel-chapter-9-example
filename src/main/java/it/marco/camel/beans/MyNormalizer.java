package it.marco.camel.beans;

import org.apache.camel.Exchange;
import org.apache.camel.language.XPath;

public class MyNormalizer {
	
    public void employeeToPerson(Exchange exchange, @XPath("/employee/name/text()") String name) {
        exchange.getOut().setBody(createPerson(name));
    }

    public void customerToPerson(Exchange exchange, @XPath("/customer/@name") String name) {
        exchange.getOut().setBody(createPerson(name));
    }

    private String createPerson(String name) {
        return "<person name=\"" + name + "\"/>";
    }
    
}