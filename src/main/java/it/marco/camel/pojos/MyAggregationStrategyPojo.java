package it.marco.camel.pojos;

public class MyAggregationStrategyPojo {

	public String aggregateMethod(String oldBody, String newBody) {
		if(oldBody!=null){
			return String.format("%s %s",oldBody,newBody);
		}else {
			return newBody;
		}
	}

}
