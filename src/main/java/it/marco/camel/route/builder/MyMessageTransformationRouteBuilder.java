package it.marco.camel.route.builder;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class MyMessageTransformationRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
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
		
		from("direct:mock-result")
			.log("from direct:mock-result ----------> ${body}");

	}

}
