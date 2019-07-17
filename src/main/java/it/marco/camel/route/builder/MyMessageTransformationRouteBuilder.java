package it.marco.camel.route.builder;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.marco.camel.strategies.MyAggregationStrategy;

public class MyMessageTransformationRouteBuilder extends RouteBuilder {
	
	public static Logger LOGGER = LoggerFactory.getLogger(MyMessageTransformationRouteBuilder.class);

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
			.pollEnrich("file://target/data/?noop=true;fileName=resource1.txt",2000)
			.to("direct:result");
		
		from("direct:start-poll-enrich-aggregation-strategy")
			.pollEnrich("file://target/data/?noop=true;fileName=resource2.txt",20000,myAggregationStrategy)
			.to("direct:result");
		
		from("direct:start-poll-enrich-dynamic")
			.pollEnrich()
				.simple("seda:${header.Endpoint}")
				.timeout(20000)
			.to("direct:result");
		
		from("seda:poll-enrich")
			.log("${body}");
		
		from("direct:resource")
			.setBody(constant("World!"));
		
		from("direct:result")
			.log("from direct:result ----------> ${body}");
		
		from("direct:mock-result")
			.log("from direct:mock-result ----------> ${body}");

	}

}
