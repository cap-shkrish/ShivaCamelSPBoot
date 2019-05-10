package SpringBootDemo.Camellama;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import SpringBootDemo.Beans.Person;

@Component
public class MyCamelHelloWorldRouter extends RouteBuilder {

	@Autowired
	public MyFilter myfilter;

	@Autowired
	private MyRedirectProcessor myProcessor;

	@Override
	public void configure() throws Exception {
		restConfiguration().component("servlet").enableCORS(true).bindingMode(RestBindingMode.json);

		rest().consumes("application/json").produces("application/json")
		.get("/filter/{name}")
			.param()
				.name("name")
				.type(RestParamType.path).dataType("string")
				.description("Who is it")
			.endParam()
		.to("direct:filter")
		.post("").description("To update the greeting message").consumes("application/xml").produces("application/xml")
		.to("direct:filter");

		from("direct:filter")
		.filter().method(myfilter, "filter")
			.log(LoggingLevel.INFO, "Inside Filter: ${body}")
			.log(LoggingLevel.INFO, "Hello World Date After: ${header.myDate}")
			.log(LoggingLevel.INFO, "Hello World Date: ${date:header.myDate:yyyy-MM-dd HH:mm:ss}")
			.to("direct:jillo")
		.end()

		.log(LoggingLevel.INFO, "Transform Simple: ${body}")
		.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
				// .convertBodyTo(ArrayList.class)
				// .setBody(simple("${body}.replaceAll('=',':')"))
				// .transform().body(ArrayList.class) //Not needed as this is automatic if json
				// array is detected
				// .split(body().method("iterator"))
				// .log(LoggingLevel.INFO, "Iterator: ${body}")
				// .end()
				// .transform().simple("${body}")
		.removeHeaders("CamelHttp*")
		.multicast().parallelProcessing()
		.to("direct:jillo", "direct:redirect");

		from("direct:jillo")
		.transform(simple("Hello ${body}"))
		.log(LoggingLevel.INFO, "direct:jillo Body: ${body}");

		from("direct:redirect")
			.process(myProcessor)
			.log(LoggingLevel.INFO, "direct:redirect Body: ${body}")
			.setHeader("Location", simple("https://www.cap.org"))
		.to("https://www.cap.org");

	}

}

@Component
class MyRedirectProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("Inside configure in MyRedirectProcessor MyRedirectProcessor\n ");
		System.out.println("exchange.getMessage().getBody(): " + exchange.getMessage().getBody());

		exchange.setPattern(ExchangePattern.InOnly);
	}

}
