package SpringBootDemo.Camellama;



import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyCamelRecepientListRouter extends RouteBuilder {

	@Autowired
	private MyRLProcessor myProcessor;

	@Override
	public void configure() throws Exception {
		System.out.println("Inside configure in MyCamelRecepientListRouter \n ");

		from("direct:myrecepientlist")
		.log(LoggingLevel.INFO, "Hello World Date After: ${header.departments}")
		//.split(body().tokenize("\n")).streaming()
		.split().jsonpath("$.[*]").streaming()
			.process(myProcessor)		
		.end()
		.recipientList(header("departments"))
		.transform().simple("${body}");

      

	}

}

@Component
class MyRLProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("\n\nInside configure in MyCamelRecepientListRouter MyRLProcessor\n ");
		System.out.println("\nexchange.getMessage().getBody(): " + exchange.getMessage().getBody());
		System.out.println("\nexchange.getIn().getBody(): " + exchange.getIn().getBody());
		
        String recipient = exchange.getIn().getBody().toString();
        String recipientQueue=recipient;
        exchange.getIn().setHeader("queue", recipientQueue);
	
		System.out.println("\n\nMyRLProcessor complete\n\n");

		//exchange.setOut(exchange.getIn());
	}

}
