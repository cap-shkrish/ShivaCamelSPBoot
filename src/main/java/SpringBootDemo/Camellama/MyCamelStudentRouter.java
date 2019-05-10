package SpringBootDemo.Camellama;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import SpringBootDemo.Beans.Student;

@Component
public class MyCamelStudentRouter extends RouteBuilder {

	@Autowired
	private MyStudentProcessor myProcessor;

	@Override
	public void configure() throws Exception {
		System.out.println("Inside configure in MyCamelStudentRouter \n ");

		String routeFromId = "direct:student";

		from(routeFromId).routeId(routeFromId)
		.log("I'm in the Camel MyCamelStudentRouter!")
		//.convertBodyTo(Student.class) 
		//Convert In message body to Student. Not needed here need to add student to type converter registry
		.process(myProcessor)
		.transform().simple("${body}");		

	}

}

@Component
class MyStudentProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("Inside configure in MyCamelStudentRouter MyStudentProcessor\n ");
		System.out.println("exchange.getMessage().getBody(): " + exchange.getMessage().getBody());
		//System.out.println("exchange.getIn().getBody(): " + exchange.getIn().getBody());

		Student student = exchange.getIn().getBody(Student.class);

		
		student.setAge(4444);
		
		System.out.println("MyStudentProcessor complete");
		exchange.getIn().setBody(student);
		exchange.setOut(exchange.getIn());
	}

}