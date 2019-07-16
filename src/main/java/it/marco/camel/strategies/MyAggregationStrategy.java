package it.marco.camel.strategies;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class MyAggregationStrategy implements AggregationStrategy {

	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		if(oldExchange!=null){
			if(oldExchange.getPattern().isOutCapable()) {
				oldExchange.getOut().setBody(String.format("%s %s", 
											 oldExchange.getIn().getBody(String.class),
											 newExchange.getIn().getBody(String.class)));
			}else {
				oldExchange.getIn().setBody(String.format("%s %s", 
						 					 oldExchange.getIn().getBody(String.class),
						 					 newExchange.getIn().getBody(String.class)));
			}
			return oldExchange;
		}else {
			return newExchange;
		}
	}

}
