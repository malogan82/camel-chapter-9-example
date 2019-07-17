package it.marco.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.main.Main;
import org.apache.camel.spring.SpringCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.marco.camel.runnable.MyRunnable;

public class SpringTest {
	
	public static Logger LOGGER = LoggerFactory.getLogger(SpringTest.class);
	
	public static void main(String[] args) {
		AbstractXmlApplicationContext appContext = new ClassPathXmlApplicationContext("camel-context.xml");
		try {
			CamelContext camelContext = SpringCamelContext.springCamelContext(appContext);
			Main main = new Main();
			main.getCamelContexts().add(camelContext);
			MyRunnable runnable = new MyRunnable(main);
			Thread thread = new Thread(runnable);
			thread.run();
			while(!main.isStarted()) {
				if(main.isStarted()) {
					break;
				}
			}
			LOGGER.info("MAIN STARTED");
			ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
//			String response1 = producerTemplate.requestBody("direct:start","Hello",String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response1));
//			String response2 = producerTemplate.requestBody("direct:start-processor","Hello",String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response2));
//			String response3 = producerTemplate.requestBody("direct:start-bean","Hello",String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response3));
//			String response4 = producerTemplate.requestBody("direct:start-enrich","Hello",String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response4));
//			String response5 = producerTemplate.requestBody("direct:start-enrich-no-aggregation","Hello",String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response5));
//			String response6 = producerTemplate.requestBody("direct:start-enrich-options","Hello",String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response6));
//			String response7 = producerTemplate.requestBodyAndHeader("direct:start-enrich-dynamic","Hello","uri","direct:resource",String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response7));
//			String response8 = producerTemplate.requestBody("direct:start-poll-enrich","Hello",String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response8));
//			String response9 = producerTemplate.requestBody("direct:start-poll-enrich-aggregation-strategy","Hello",String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response9));
//			producerTemplate.sendBody("seda:poll-enrich","World!");
//			String response10 = producerTemplate.requestBodyAndHeader("direct:start-poll-enrich-dynamic", 
//																	 "Hello", 
//																	 "Endpoint", 
//																	 "poll-enrich", 
//																	 String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response10));
//			producerTemplate.sendBody("seda:poll-enrich","World!");
//			String response11 = producerTemplate.requestBody("direct:start-poll-enrich-options","Hello",String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response11));
//			String xmlBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
//							 "<bar xmlns=\"http://example.com/person\">TEST</bar>";
//			String response12 = producerTemplate.requestBody("direct:xpath-filter",xmlBody,String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response12));
//			String xmlBodyEmployee = "<employee><name>Mario</name>EMPLOYEE</employee>";
//			String xmlBodyCustomer = "<customer name=\"Antonio\">CUSTOMER</customer>";
//			String response13 = producerTemplate.requestBody("direct:start-normalizer",xmlBodyEmployee,String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response13));
//			String response14 = producerTemplate.requestBody("direct:start-normalizer",xmlBodyCustomer,String.class);
//			LOGGER.info(String.format("RESPONSE ----------> %s",response14));
			try {
				Thread.sleep(10000);
				main.stop();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
			}
			LOGGER.info("MAIN STOPPED");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

}
