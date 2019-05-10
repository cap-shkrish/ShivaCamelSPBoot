package SpringBootDemo.Camellama;

import java.util.Arrays;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyCamelFileRouter extends RouteBuilder {

	@Autowired
	private MyFileProcessor myProcessor;
	
	@Override
	public void configure() throws Exception {

		//moveFile();
		multiFileProcessorFile();
	}
	
	public void moveFile()
	{
		System.out.println("Inside configure \n ");		
		from("file:C:/temp/poc/source?noop=true")
		//.filter(header(Exchange.FILE_NAME).startsWith("MyFile"))
		.filter(bodyAs(String.class).contains("Pele"))
		.process(myProcessor)
		.to("file:C:/temp/poc/target?fileName=records.csv&noop=true");
		
		System.out.println("File Transferred \n ");		
	}
	
	public void multiFileProcessorFile()
	{
		System.out.println("Inside multiFileProcessorFile \n ");		
		from("file:C:/temp/poc/source?noop=true")
		//.filter(header(Exchange.FILE_NAME).startsWith("MyFile"))
		.unmarshal().csv().split(body().tokenize(","))
		.choice()
			.when(bodyAs(String.class).contains("closed"))
				.to("file:C:/temp/poc/target?fileName=close.csv&noop=true")
			.when(bodyAs(String.class).contains("pending"))
				.to("file:C:/temp/poc/folka?fileName=pending.csv&noop=true")
		.endChoice();
		System.out.println("File Processed \n ");		
	}
}


@Component
class MyFileProcessor implements Processor {
	
	
	StringBuilder sb = new StringBuilder();
	
	public void process(Exchange exchange) throws Exception {

		String body = exchange.getIn().getBody(String.class);
		Arrays.stream(body.split(" ")).forEach(s->{
													sb.append(s+",");
												  });
		
		
		exchange.getIn().setBody(sb);
	}
}
