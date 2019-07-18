package it.marco.camel.beans;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBean {
	
	public static Logger LOGGER = LoggerFactory.getLogger(MyBean.class);
	
	public String transform(String input) {
		return String.format("%s World!", input);
	}
	
	public void processLine(List<String> list) {
		for(String string:list) {
			LOGGER.info(string);
		}
	}

}
