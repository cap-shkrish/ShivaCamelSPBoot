package SpringBootDemo.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringBootDemo.Beans.Person;
import SpringBootDemo.Beans.Student;
import SpringBootDemo.Services.CamelRouterService;

@CrossOrigin
@RestController
@RequestMapping("/helloworld")
public class HelloWorldController {

	@Autowired
	private CamelRouterService camelRouterService;

	@GetMapping(produces = "application/json")
	public Map<String, String> GetStudents() throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("\n helloworld", " Welcome to my Hello World !! ");
		return map;
	}

	@PostMapping(value = "/cameltest")
	public ResponseEntity<String> hello(final HttpServletRequest request, @RequestBody String requestBody) 
	{
		final String routeToId = "direct:hello";		
		String responseBody = camelRouterService.send(requestBody, routeToId);
		//int responseCode = responseExchange.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class).intValue();
		return ResponseEntity.ok(responseBody);
	}
	
	@PostMapping(value = "/camelstudent")
	public ResponseEntity<String> student(final HttpServletRequest request, @RequestBody Student requestBody) 
	{
		final String routeToId = "direct:student";		
		String responseBody = camelRouterService.send(requestBody, routeToId);
		return ResponseEntity.ok(responseBody);
	}
	
	@PostMapping(value = "/camelmulti")
	public ResponseEntity<String> multi(final HttpServletRequest request, @RequestBody String requestBody) 
	{
		final String routeToId = "direct:multi";		
		String responseBody = camelRouterService.send(requestBody, routeToId);
		return ResponseEntity.ok(responseBody);
	}
	
	@PostMapping(value = "/camelchoice")
	public ResponseEntity<String> choice(final HttpServletRequest request, @RequestBody String requestBody) 
	{
		final String routeToId = "direct:mychoice";
		String responseBody = camelRouterService.send(requestBody, routeToId);
		return ResponseEntity.ok(responseBody);
	}

	@PostMapping(value = "/camelsg")
	public ResponseEntity<String> scattergather(final HttpServletRequest request, @RequestBody String requestBody) 
	{
		final String routeToId = "direct:myscattergather";		
		String responseBody = camelRouterService.send(requestBody, routeToId);
		return ResponseEntity.ok(responseBody);
	}
	
	@PostMapping(value = "/camelrl")
	public ResponseEntity<String> recepientList(final HttpServletRequest request, @RequestBody String requestBody) 
	{
		final String routeToId = "direct:myrecepientlist";
		Map<String,Object> headerMap = new HashMap<String,Object>();
		headerMap.put("departments", "direct:sello1,direct:sello2,direct:zello");		
		String responseBody = camelRouterService.sendBodyAndHeaders(requestBody, routeToId, headerMap);
		return ResponseEntity.ok(responseBody);
	}
	
	@PostMapping(value = "/camelsplit")
	public ResponseEntity<String> splitAgg(final HttpServletRequest request, @RequestBody String requestBody) 
	//public ResponseEntity<String> splitAgg(final HttpServletRequest request, @RequestBody ArrayList<Person> requestBody) 
	{
		final String routeToId = "direct:splitagg";		
		String responseBody = camelRouterService.send(requestBody, routeToId);
		return ResponseEntity.ok(responseBody);
	}
	
	@PostMapping(value = "/camelfilter")
	public ResponseEntity<String> filter(final HttpServletRequest request, 
												@RequestBody String requestBody,
												@RequestHeader("myDate") String myDate) 
	//public ResponseEntity<String> filter(final HttpServletRequest request, 
	//		@RequestBody ArrayList<Person> requestBody,
	//		@RequestHeader("myDate") String myDate) 
	{
		final String routeToId = "direct:filter";		
		Map<String,Object> headerMap = new HashMap<String,Object>();
		headerMap.put("myDate", myDate);
		String responseBody = camelRouterService.sendBodyAndHeaders(requestBody, routeToId, headerMap);				
		return ResponseEntity.ok(responseBody);
	}


}
