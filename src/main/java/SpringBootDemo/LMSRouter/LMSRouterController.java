package SpringBootDemo.LMSRouter;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringBootDemo.Services.CamelRouterService;

@CrossOrigin
@RestController
@RequestMapping("/lmsrouter")
public class LMSRouterController {
	
	@Autowired
	RouterDBService routerDBService;

	@Autowired
	private CamelRouterService camelRouterService;

	//Example Only
	@PostMapping(value = "/inbound")
	public ResponseEntity<String> inbound(final HttpServletRequest request, @RequestBody String requestBody) 
	{
		final String routeToId = "direct:lmsRouter";		
		String responseBody = camelRouterService.send(requestBody, routeToId);
		return ResponseEntity.ok(responseBody);
	}

	@GetMapping(value = "/outbound")
	public ResponseEntity<String> outbound(final HttpServletRequest request, @RequestBody String requestBody) 
	{		
		final String routeToId = "direct:lmsRouter";
		String responseBody = camelRouterService.send(requestBody, routeToId);
		return ResponseEntity.ok(responseBody);
	}


}
