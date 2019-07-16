package it.marco.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class MyProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
        in.setBody(in.getBody(String.class) + " World!");
	}

}
