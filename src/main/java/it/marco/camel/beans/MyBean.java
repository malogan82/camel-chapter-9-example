package it.marco.camel.beans;

public class MyBean {
	
	public String transform(String input) {
		return String.format("%s World!", input);
	}

}
