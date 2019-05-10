package SpringBootDemo.Camellama;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyCamelRestRouter extends RouteBuilder {

	@Autowired
	private MyRestProcessor myProcessor;

	@Override
	public void configure() throws Exception {
		System.out.println("Inside configure in MyCamelRestRouter \n ");

		String routeFromId = "direct:hello";
		String routeToId = "direct:zello";

		from(routeFromId).routeId(routeFromId)
		.log("I'm in the Camel Route!")
		.process(myProcessor)
		.transform()
		.simple("${body}")
		//.to("file:C:/temp/poc/target?fileName=rest.csv&noop=true");
		.to(routeToId);
	}

}

@Component
class MyRestProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("Inside configure in MyCamelRestRouter MyRestProcessor\n ");
		System.out.println("exchange.getMessage().getBody(): " + exchange.getMessage().getBody());
		//System.out.println("exchange.getIn().getBody(): " + exchange.getIn().getBody());

		String myString = exchange.getIn().getBody(String.class);
		String[] lineSeparator = myString.split(System.getProperty("line.separator"));
		//System.out.println("lineSeparator : " + lineSeparator.length);
		
		StringBuffer sb = new StringBuffer();

		int i = 0;
		for (String lineData : lineSeparator) {
			
			String[] commaSeparator = lineData.split(",");
			//System.out.println("commaSeparator : " + commaSeparator[0]);
			String[] colonSeparator = commaSeparator[0].split(":");
			if(colonSeparator[0].contains("age"))
				sb.append("\"aaagggeee\":" + colonSeparator[1]);
			else	
				sb.append(commaSeparator[0]);
			if(commaSeparator[0].startsWith("{") ||
					commaSeparator[0].endsWith("}"))
				sb.append("");
			else if(i < lineSeparator.length-2)
			{
				sb.append(" | ");
				
			}
			i++;
			sb.append(System.getProperty("line.separator"));
		}
		
		System.out.println("MyProcessor complete");
		exchange.getIn().setBody(sb.toString());
	}

}