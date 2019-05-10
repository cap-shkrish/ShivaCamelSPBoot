package SpringBootDemo.Controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="HelloController API", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class HelloRestController {

	@Value("${message}")
	private String message;
	
	@ApiOperation("Returns the Hello World Page")
	@ApiResponses (value= {
			@ApiResponse(code=200, message="OK", response=String.class)
			})

	@GetMapping("/hello")
	public String sayHello()
	{
		return message;
	}
	
	  @GetMapping(value="/testRedirect/{name}")
	  void handleRedirect(HttpServletResponse response, @PathVariable("name") String name) throws IOException {
		  
		System.out.println("Inside handleRedirect response : " + name);
	    response.sendRedirect("https://www.cap.org");
	    
	  }
	
	
}
