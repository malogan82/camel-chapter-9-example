package it.marco.camel.strategies;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.marco.camel.Test;

public class MyAggregationFileStrategy implements AggregationStrategy {
	
	public static Logger LOGGER = LoggerFactory.getLogger(MyAggregationFileStrategy.class);

	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		if(oldExchange!=null){
			String fileContent = "";
			File file = null;
			FileInputStream fis = null;
			try {
				file = newExchange.getIn().getBody(File.class);
				fis = new FileInputStream(file);
				int i;
				while((i=fis.read())!=-1){ 
					fileContent+=(char)i;
				}
				if(fis!=null) {
					fis.close();
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage(),e);
			} 
			if(oldExchange.getPattern().isOutCapable()) {
				oldExchange.getOut().setBody(String.format("%s %s", 
											 oldExchange.getIn().getBody(String.class),
											 fileContent));
			}else {
				oldExchange.getIn().setBody(String.format("%s %s", 
						 					oldExchange.getIn().getBody(String.class),
						 					fileContent));
			}
			return oldExchange;
		}else {
			return newExchange;
		}
	}

}
