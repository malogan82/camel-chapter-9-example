package it.marco.camel.route.builder;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import it.marco.camel.strategies.MyAggregationStrategy;

public class MyMessageTransformationRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
		AggregationStrategy myAggregationStrategy = new MyAggregationStrategy();
		
		from("direct:start")
			.setBody(body().append(" World!")).to("direct:mock-result");
		
		from("direct:start-processor")
			.process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					Message in = exchange.getIn();
			        in.setBody(in.getBody(String.class) + " World!");
				}
			})
			.to("direct:mock-result");
		
		from("direct:start-bean")
			.beanRef("myBean","transform")
			.to("direct:mock-result");
		
		from("direct:start-enrich")
			.enrich("direct:resource",myAggregationStrategy)
			.to("direct:mock-result");
		
		from("direct:start-enrich-no-aggregation")
			.enrich("direct:resource")
			.to("direct:mock-result");
		
		from("direct:start-enrich-dynamic")
			.enrich().simple("${header.uri}")
			.to("direct:result");
		
		from("direct:start-poll-enrich")
			.pollEnrich("file://target/data/?fileName=resource.txt",5000,myAggregationStrategy)
			.to("direct:result");
		
		from("direct:resource")
			.setBody(constant("World!"));
		
		from("direct:result")
			.log("from direct:result ----------> ${body}");
		
		from("direct:mock-result")
			.log("from direct:mock-result ----------> ${body}");

	}

}
