package it.marco.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.marco.camel.beans.MyBean;
import it.marco.camel.beans.MyNormalizer;
import it.marco.camel.route.builder.MyMessageTransformationRouteBuilder;
import it.marco.camel.runnable.MyRunnable;

public class Test {
	
	public static Logger LOGGER = LoggerFactory.getLogger(Test.class);

	public static void main(String[] args) {
		Main main = new Main();
		main.bind("myBean", new MyBean());
		main.bind("myNormalizer", new MyNormalizer());
		main.addRouteBuilder(new MyMessageTransformationRouteBuilder());
		MyRunnable runnable = new MyRunnable(main);
		Thread thread = new Thread(runnable);
		thread.run();
		while(!main.isStarted()) {
			if(main.isStarted()) {
				break;
			}
		}
		LOGGER.info("MAIN STARTED");
		CamelContext camelContext = main.getCamelContexts().get(0);
		ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
		String response1 = producerTemplate.requestBody("direct:start","Hello",String.class);
		LOGGER.info(String.format("RESPONSE ----------> %s",response1));
		String response2 = producerTemplate.requestBody("direct:start-processor","Hello",String.class);
		LOGGER.info(String.format("RESPONSE ----------> %s",response2));
		String response3 = producerTemplate.requestBody("direct:start-bean","Hello",String.class);
		LOGGER.info(String.format("RESPONSE ----------> %s",response3));
		String response4 = producerTemplate.requestBody("direct:start-enrich","Hello",String.class);
		LOGGER.info(String.format("RESPONSE ----------> %s",response4));
		String response5 = producerTemplate.requestBody("direct:start-enrich-no-aggregation","Hello",String.class);
		LOGGER.info(String.format("RESPONSE ----------> %s",response5));
		String response6 = producerTemplate.requestBodyAndHeader("direct:start-enrich-dynamic","Hello","uri","direct:resource",String.class);
		LOGGER.info(String.format("RESPONSE ----------> %s",response6));
		String response7 = producerTemplate.requestBody("direct:start-poll-enrich","Hello",String.class);
		LOGGER.info(String.format("RESPONSE ----------> %s",response7));
		String response8 = producerTemplate.requestBody("direct:start-poll-enrich-aggregation-strategy","Hello",String.class);
		LOGGER.info(String.format("RESPONSE ----------> %s",response8));
		producerTemplate.sendBody("seda:poll-enrich","World!");
		String response9 = producerTemplate.requestBodyAndHeader("direct:start-poll-enrich-dynamic", 
																 "Hello", 
																 "Endpoint", 
																 "poll-enrich", 
																 String.class);
		LOGGER.info(String.format("RESPONSE ----------> %s",response9));
		String xmlBodyEmployee = "<employee><name>Mario</name>EMPLOYEE</employee>";
		String xmlBodyCustomer = "<customer name=\"Antonio\">CUSTOMER</customer>";
		String response10 = producerTemplate.requestBody("direct:start-normalizer",xmlBodyEmployee,String.class);
		LOGGER.info(String.format("RESPONSE ----------> %s",response10));
		String response11 = producerTemplate.requestBody("direct:start-normalizer",xmlBodyCustomer,String.class);
		LOGGER.info(String.format("RESPONSE ----------> %s",response11));
		try {
			Thread.sleep(10000);
			main.stop();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		LOGGER.info("MAIN STOPPED");
		
	}
	
}
