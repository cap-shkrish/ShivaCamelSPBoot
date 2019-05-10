package SpringBootDemo.Camellama;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import SpringBootDemo.Beans.Student;

@Component
public class MyCamelSecondFileWriter extends RouteBuilder {

	@Autowired
	private MySecondProcessor myProcessor;

	@Override
	public void configure() throws Exception {
		System.out.println("Inside configure in MyCamelFileWriter \n ");

		String routeFromId = "direct:sello1";
		String routeFromId2 = "direct:sello2";

		from(routeFromId).routeId(routeFromId)
		.log("\nI'm in the direct:sello1 MySecondProcessor Route! from Multi\n")
		.process(myProcessor)
		//.transform().simple("${body}")
		.to("file:C:/temp/poc/target?fileName=second.csv&noop=true");
		
		from(routeFromId2).routeId(routeFromId2)
		.log("\nI'm in the direct:sello2 Camel MySecondProcessor! from Multi\n")
		.process(myProcessor)
		//.transform().simple("${body}")
		.to("file:C:/temp/poc/target?fileName=third.csv&noop=true");
		

	}

}

@Component
class MySecondProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("Inside configure in MyCamelSecondFileWriter MySecondProcessor\n ");
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
		System.out.println("\nMySecondProcessor complete\n");
		System.out.println(sb.toString());
		exchange.getIn().setBody(sb.toString());		
		
	}

}