package SpringBootDemo.Camellama;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import SpringBootDemo.Beans.Student;

@Component
public class MyCamelThirdFileWriter extends RouteBuilder {

	@Autowired
	private MyThirdProcessor myProcessor;

	@Override
	public void configure() throws Exception {
		System.out.println("Inside configure in MyCamelThirdFileWriter \n ");

		String routeFromId = "direct:sello3";

		from(routeFromId).routeId(routeFromId)
		.log("I'm in the Camel Route!")
		.process(myProcessor)

        .marshal().json(JsonLibrary.Jackson)
        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
        .enrich("http://shivaebsspringboot-env.c5bttzuiwh.us-west-2.elasticbeanstalk.com/students").log("${body}");
        //.setHeader(Exchange.HTTP_METHOD, constant("GET"))
	    //.enrich("http://localhost:8080/students").log("${body}");
		
	}
	
	@Autowired
	private ProducerTemplate producer;


}

@Component
class MyThirdProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("Inside configure in MyCamelThirdFileWriter MyProcessor\n ");
		System.out.println("exchange.getMessage().getBody(): " + exchange.getMessage().getBody());
		System.out.println("exchange.getIn().getBody(): " + exchange.getIn().getBody());

		String myString = exchange.getIn().getBody(String.class);
		String[] lineSeparator = myString.split(System.getProperty("line.separator"));
		System.out.println("lineSeparator : " + lineSeparator.length);
		
		StringBuffer sb = new StringBuffer();
		sb.append(System.getProperty("line.separator"));
		int i = 0;
		for (String lineData : lineSeparator) {
			System.out.println("lineData : " + lineData);
			String str = lineData.replace(" | ", ",").replaceAll("aaagggeee", "age");
			
			sb.append(str);
			sb.append(System.getProperty("line.separator"));

		}		
		System.out.println("MyProcessor complete\n");
		System.out.println(sb.toString());
		//exchange.getIn().setBody(sb.toString());
		
		//Convert json string to student object
		Student std = new ObjectMapper().readValue(sb.toString(), Student.class);
		std.setAge(444);
		exchange.getIn().setBody(std);
		
	}

}