package SpringBootDemo.Services;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamelRouterService {

	@Autowired
	private ProducerTemplate producer;

	@Autowired
	private CamelContext camelContext;

	//Use if request body of object type such as Person or Student etc
	public String send(Object requestBody, String routeToId) {
		System.out.println("requestBody: " + requestBody);
		Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(requestBody).build();
		requestExchange.setProperty("myproperty", requestBody);
		Exchange responseExchange = producer.send(routeToId, requestExchange);
		String responseBody = responseExchange.getOut().getBody(String.class);
		System.out.println("\n responseBody: \n" + responseBody);
		return responseBody;
		
	}

	//Use is Request body of a JSON extracted as a string
	public String send(String requestBody, String routeToId) {
		System.out.println("requestBody: " + requestBody);
		Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(requestBody).build();
		requestExchange.setProperty("myproperty", requestBody);
		Exchange responseExchange = producer.send(routeToId, requestExchange);
		String responseBody = responseExchange.getOut().getBody(String.class);
		System.out.println("\n responseBody: \n" + responseBody);
		return responseBody;
	}
	
	//Use if Request contains headers and a body of a JSON extracted as a string
	public String sendBodyAndHeaders(String requestBody, String routeToId, Map headerMap) {

		System.out.println("requestBody: " + requestBody);
		System.out.println("headerMap: " + headerMap);
		Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(requestBody).build();
		//producer.sendBodyAndHeader(routeToId, requestBody, "myDate", myDate);		
		producer.sendBodyAndHeaders(routeToId, requestBody, headerMap);
		String responseBody = "Called sendBodyAndHeaders Route";
		System.out.println("\n responseBody: \n" + responseBody);
		return responseBody;
	}
	
	//Use if Request contains headers and a body of a JSON extracted as a string
	public String sendBodyAndHeaders(Object requestBody, String routeToId, Map headerMap) {

		System.out.println("requestBody: " + requestBody);
		System.out.println("headerMap: " + headerMap);
		Exchange requestExchange = ExchangeBuilder.anExchange(camelContext).withBody(requestBody).build();
		//producer.sendBodyAndHeader(routeToId, requestBody, "myDate", myDate);		
		producer.sendBodyAndHeaders(routeToId, requestBody, headerMap);
		String responseBody = "Called Object Filter Route";
		System.out.println("\n responseBody: \n" + responseBody);
		return responseBody;
	}


}
