package SpringBootDemo.Camellama;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import SpringBootDemo.Beans.Student;

@Component
public class MyCamelFileWriter extends RouteBuilder {

	@Autowired
	private MyProcessor myProcessor;

	@Override
	public void configure() throws Exception {
		System.out.println("Inside configure in MyCamelFileWriter \n ");

		String routeFromId = "direct:zello";

		from(routeFromId).routeId(routeFromId)
		.log("I'm in the Camel Route!")
		.process(myProcessor)
		.transform().simple("${body}");
		
		

	}

}

@Component
class MyProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("Inside configure in MyCamelFileWriter MyProcessor\n ");
		System.out.println("exchange.getMessage().getBody(): " + exchange.getMessage().getBody());
		//System.out.println("exchange.getIn().getBody(): " + exchange.getIn().getBody());

		String myString = exchange.getIn().getBody(String.class);
		String[] lineSeparator = myString.split(System.getProperty("line.separator"));
		//System.out.println("lineSeparator : " + lineSeparator.length);
		
		StringBuffer sb = new StringBuffer();
		sb.append(System.getProperty("line.separator"));
		int i = 0;
		for (String lineData : lineSeparator) {
			//System.out.println("lineData : " + lineData);
			String str = lineData.replace(" | ", ",").replaceAll("aaagggeee", "age");
			
			sb.append(str);
			sb.append(System.getProperty("line.separator"));

		}		
		System.out.println("MyProcessor complete\n");
		System.out.println(sb.toString());
		exchange.getIn().setBody(sb.toString());
		exchange.setOut(exchange.getIn());
		//Convert json string to student object
		//Student std = new ObjectMapper().readValue(sb.toString(), Student.class);
		//exchange.getIn().setBody(std);
		
		
	}

}