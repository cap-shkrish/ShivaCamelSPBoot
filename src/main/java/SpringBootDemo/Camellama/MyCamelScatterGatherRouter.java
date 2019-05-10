package SpringBootDemo.Camellama;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyCamelScatterGatherRouter extends RouteBuilder {

	@Autowired
	private MyProcessor1 myProcessor1;
	@Autowired
	private MySGProcessor2 myProcessor2;
	@Autowired
	private MySGProcessor3 myProcessor3;
	@Autowired
	private MySGProcessor4 myProcessor4;

	@Autowired
	private MyAggregationStrategy myAggregationStrategy;

	@Override
	public void configure() throws Exception {
		System.out.println("Inside configure in MyCamelScatterGatherRouter \n ");

		String routeFromId = "direct:myscattergather";
		from(routeFromId).routeId(routeFromId)
		.process(myProcessor3)
		.multicast(myAggregationStrategy)
		.to("direct:A", "direct:B")
		.end()
		.log("\nI'm in the Camel MyCamelScatterGatherRouter!")
		.transform().simple("${body}")
		.process(myProcessor4);

		from("direct:A")
		.process(myProcessor1);
		from("direct:B")
		.process(myProcessor2);

	}

}

@Component
class MyAggregationStrategy implements AggregationStrategy {
	
	@Override
	public org.apache.camel.Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		String oldBody;
		String newBody;
		if(oldExchange == null) {
			oldExchange = newExchange;
			oldBody = oldExchange.getIn().getBody(String.class);
		
			oldExchange.getIn().setBody(oldBody);
		}
		else {
			oldBody = oldExchange.getIn().getBody(String.class);
			newBody = newExchange.getIn().getBody(String.class);
		
			oldExchange.getIn().setBody(  oldBody + "," + System.getProperty("line.separator") + newBody);
		}		
		
		System.out.println("\n\n Aggregate called with oldExchange = \n\n" + oldExchange.getIn().getBody());
				
		return oldExchange;
	}
}

@Component
class MyProcessor1 implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("\n\nInside configure in MyProcessor1 \n ");
		System.out.println("exchange.getMessage().getBody(): " + exchange.getMessage().getBody());
		//System.out.println("exchange.getIn().getBody(): " + exchange.getIn().getBody());
		
		JSONObject jo = new JSONObject(exchange.getIn().getBody().toString());
		jo.put("first_name", "Venka Venka");
		jo.put("last_name", "Konka Konka");
		jo.put("age", 7777);
		exchange.getIn().setBody(jo.toString());

		//Convert json string to student object
/*		Person std = exchange.getMessage().getBody( Person.class);
		std.setFirst_name("dfkjdshfkldslj");
		std.setLast_name("jjjjjjjjjjjj");

		JSONObject jo = new JSONObject(std);
		exchange.getIn().setBody(jo.toString());
*/
	}

}

@Component
class MySGProcessor2 implements Processor {
	
	

	public void process(Exchange exchange) throws Exception {

		System.out.println("\n\nInside configure in MySGProcessor2 \n ");
		System.out.println("exchange.getMessage().getBody(): " + exchange.getMessage().getBody());
		//System.out.println("exchange.getIn().getBody(): " + exchange.getIn().getBody());
		
		JSONObject jo = new JSONObject(exchange.getIn().getBody().toString());
		jo.put("first_name", "Plerepaaliki");
		jo.put("last_name", "Zindabad");
		
		JSONObject jadd = jo.getJSONObject("address");
		jadd.put("state", "Netherlands");
		jadd.put("country", "France");
		jadd.put("address1", "1234 East Alameda Avenue");
		jadd.put("address2", "4321 West Alameda Street");
		jo.put("address", jadd);
		
		exchange.getIn().setBody(jo.toString());

		
		
/*		//Convert json string to student object
		Person std = exchange.getMessage().getBody(Person.class);
		std.setFirst_name("iiiiiiiiiiiiiii");
		std.setLast_name("ooooooooooo");
		Address add = std.getAddress();
		add.setState("Netherlands");
		std.setAddress(add);

		JSONObject jo = new JSONObject(std);
		exchange.getIn().setBody(jo.toString());*/

	}

}

@Component
class MySGProcessor3 implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("\n\nInside configure in MySGProcessor3 \n ");
		System.out.println("exchange.getMessage().getBody(): " + exchange.getMessage().getBody());
		//System.out.println("exchange.getIn().getBody(): " + exchange.getIn().getBody());
		//Convert json string to student object
		//Person std = new ObjectMapper().readValue(exchange.getMessage().getBody().toString(), Person.class);
		//exchange.getIn().setBody(std);

	}

}

@Component
class MySGProcessor4 implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("\n\nInside configure in MySGProcessor4 \n ");
		System.out.println("exchange.getMessage().getBody(): \n" + exchange.getMessage().getBody());
		//System.out.println("exchange.getIn().getBody(): " + exchange.getIn().getBody());
		exchange.setOut(exchange.getIn());
	}

}

