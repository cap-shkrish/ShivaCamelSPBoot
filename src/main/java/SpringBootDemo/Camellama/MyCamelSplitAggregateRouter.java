package SpringBootDemo.Camellama;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.json.simple.JsonArray;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import SpringBootDemo.Beans.Person;

@Component
public class MyCamelSplitAggregateRouter extends RouteBuilder {

	@Autowired
	private MySplitAggregationStrategy myAggregationStrategy;
	@Autowired
	private MyJSONProcessor myProcessor;

	@Override
	public void configure() throws Exception {
		System.out.println("Inside configure in MyCamelSplitAggregateRouter \n ");

		String routeFromId = "direct:splitagg";

		from(routeFromId).routeId(routeFromId)
		.log("I'm in the Camel MyCamelSplitAggregateRouter!")
		//Pass a Person object in Controller instead of ArrayList<Person>
		//.split(body().method("getAddress"), myAggregationStrategy)
		//Pass a String object in Controller to use JSONPATH
		//This Splits an array of json objects in the forn[{},{}...]
		// and calls the aggregationstrategy after the end statement for split.
		//This split-end loop is executed for every object split from the JSONArray {}
		.split().jsonpath("$.[*]").aggregationStrategy(myAggregationStrategy)
		//Pass ArrayList<Person> in Controller to use the iterator
		//.split(body().method("iterator"), myAggregationStrategy) 
			.process(myProcessor)  // processor gets called for every iterator element loop
		.end() //myAggregationStrategy gets called at the end of every split loop iteration
		.transform().simple("${body}");
	}
}

@Component
class MyJSONProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		String str = exchange.getMessage().getBody(String.class);
		String str1 = exchange.getIn().getBody(String.class);
		String str2 = exchange.getProperty("myproperty", String.class);
		System.out.println("\n\n String from message body: " + str );
		//System.out.println("\n\n String from Property: " + str2 );
		str = str.replaceAll("=", ":");
		exchange.getIn().setBody(str);

	}
}

@Component
class MySplitAggregationStrategy implements AggregationStrategy {
	
	private int i = 0;
	
	@Override
	public org.apache.camel.Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		System.out.println("\n\n Inside MySplitAggregationStrategy aggregate() Aggregating split objects \n\n");
		System.out.println("\n\n NewExchange: " + newExchange.getIn().getBody().toString() );

		if(oldExchange == null) {
			oldExchange = newExchange;
			JSONObject jo = new JSONObject(oldExchange.getIn().getBody(String.class));
			jo.put("first_name", "Venka Venka");
			jo.put("last_name", "Konka Konka");
			jo.put("age", 7777);
			oldExchange.getIn().setBody(jo.toString());
		}
		else if(this.i%2==0){					  
			i++;
			String oldBody = oldExchange.getIn().getBody(String.class);

			JSONObject jo = new JSONObject(newExchange.getIn().getBody().toString());
			jo.put("first_name", "Plerepaaliki");
			jo.put("last_name", "Zindabad");
			
			JSONObject jadd = jo.getJSONObject("address");
			jadd.put("state", "Netherlands");
			jadd.put("country", "France");
			jadd.put("address1", "1234 East Alameda Avenue");
			jadd.put("address2", "4321 West Alameda Street");
			jo.put("address", jadd);
			
			newExchange.getIn().setBody(jo.toString());
			
			String newBody = newExchange.getIn().getBody(String.class);

			oldExchange.getIn().setBody(  oldBody + "," + System.getProperty("line.separator") + newBody);
		}		
		else
		{
			i++;
			String oldBody = oldExchange.getIn().getBody(String.class);

			JSONObject jo = new JSONObject(newExchange.getIn().getBody().toString());
			jo.put("first_name", "TeraTaffil");
			jo.put("last_name", "ZinguTaffel");
			
			JSONObject jadd = jo.getJSONObject("address");
			jadd.put("state", "Finland");
			jadd.put("country", "Helsinki");
			jadd.put("address1", "3333 Yellow Stone National Park");
			jadd.put("address2", "8888 Grand Teton National Park");
			jo.put("address", jadd);
			
			newExchange.getIn().setBody(jo.toString());
			
			String newBody = newExchange.getIn().getBody(String.class);

			oldExchange.getIn().setBody(  oldBody + "," + System.getProperty("line.separator") + newBody);
			
		}
		System.out.println("\n\n Aggregate called with oldExchange = \n\n" + oldExchange.getIn().getBody());
				
		return oldExchange;
	}
}