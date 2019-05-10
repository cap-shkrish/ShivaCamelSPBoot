package SpringBootDemo;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

	
	/**
	 * 
	 * @param builder
	 * @return
	 * Spring Boot no longer automatically defines a RestTemplate 
	 * but instead defines a RestTemplateBuilder allowing you more 
	 * control over the RestTemplate that gets created. You can inject 
	 * the RestTemplateBuilder as an argument in your @Bean method to 
	 * create a RestTemplate:
	 */
	
	 @Bean
	  public RestTemplate restTemplate(RestTemplateBuilder builder) {
	     // Do any additional configuration here
	     return builder.build();
	  }
}
