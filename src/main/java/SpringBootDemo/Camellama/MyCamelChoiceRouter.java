package SpringBootDemo.Camellama;

import org.apache.camel.CamelException;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import SpringBootDemo.Beans.Person;

@Component
public class MyCamelChoiceRouter extends RouteBuilder {

	@Autowired
	private MyChoiceProcessor myProcessor;

	@Override
	public void configure() throws Exception {

		//send a readable error message back to the client and handle the error internally
		onException(Exception.class)
		    .handled(true)
		    .setBody(body())
		    .log("An Error was detected in the JSON format !")
		    .process(new Processor() {
		         public void process(Exchange exchange) throws Exception {
		               Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
		               System.out.println(exception.getLocalizedMessage());
		               System.out.println("Inside Exception Processor \n " + 
		            		   exchange.getIn().getBody());
		         }
		    })
		    .transform().simple("${body}");

		//ignore the exception and continue the route (can be dangerous, use wisely)
		//onException(CamelException.class)
		 //   .continued(true);

		System.out.println("Inside configure in MyCamelChoiceRouter \n ");

		String routeFromId = "direct:mychoice";
		Predicate p1 = body().contains("address");
		Predicate p2 = jsonpath("$.address[?(@.state=='Germany')]");
		Predicate cond = PredicateBuilder.and(p1, p2);

		from(routeFromId).routeId(routeFromId)
		.log("I'm in the Camel MyCamelChoiceRouter!")
			.choice()
				// .when().jsonpath("$.address[?(@.state=='Germany')]") Not needed
				// .when(body().contains("address")) not needed if you use predicates
				.when(cond)
				// .choice() not needed if you use predicates
				// .when().jsonpath("$.address[?(@.state=='Germany')]") not needed if you use
				// predicates
				.log("I'm in .when().jsonpath(\"$.address[?(@.state=='Germany')]\")!")
				.process(myProcessor).marshal()
				.json(JsonLibrary.Jackson)
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				// .enrich("https://vlt91bxrai.execute-api.us-east-1.amazonaws.com/shivalambda/shivalambdaresource")
				.enrich("https://s40y5e5lt8.execute-api.us-east-1.amazonaws.com/person/dynamodb")
				// .otherwise() not needed if you use predicates
				// .log("I'm in Otherwise as State is NOT GERMANY!")
				// .endChoice() not needed if you use predicates
				.otherwise().log("I'm in Otherwise direct:multi as State is NOT GERMANY!!")
					.to("direct:multi")
			.end()
			.transform().simple("${body}");
		
			
	}

}

@Component
class MyChoiceProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("\nInside configure in MyChoiceProcessor \n ");
		System.out.println("exchange.getMessage().getBody(): " + exchange.getMessage().getBody());
		//System.out.println("exchange.getIn().getBody(): " + exchange.getIn().getBody());
		
		// Convert json string to student object
		Person std = new ObjectMapper().readValue(exchange.getMessage().getBody().toString(), Person.class);
		exchange.getIn().setBody(std);
		exchange.setOut(exchange.getIn());
	}

}
