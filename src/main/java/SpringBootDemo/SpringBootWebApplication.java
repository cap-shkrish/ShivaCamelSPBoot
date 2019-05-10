package SpringBootDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableTransactionManagement
@EnableJms
@RestController
@SpringBootApplication
@ComponentScan(basePackages = {"SpringBootDemo"})
@EntityScan("SpringBootDemo")
public class SpringBootWebApplication  extends SpringBootServletInitializer {

	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication .class, args);
	}
	
	   @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(SpringBootWebApplication.class);
	    }
  

}
