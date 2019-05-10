package SpringBootDemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource("classpath:swagger.properties")
@ComponentScan(basePackages = { "SpringBootDemo.Controllers" })
public class SwaggerConfig {

	@Value("${person.id}")
	private String description;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("user-api")
		        .useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				//.paths(PathSelectors.regex("/.*"))
				.build()
				.apiInfo(apiInfo());
	}

	@Bean
	public ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	        //.title("User API")
	        .description(description)
	        .version("1.0.0")
	        .contact(new Contact("John Doe", "www.example.com", "myeaddress@company.com"))
	        .license("License of API")
	        .licenseUrl("License of API")
	        .build();
	}
	
	@Bean
	public UiConfiguration uiConfiguration() {
	    return UiConfigurationBuilder.builder()
	        .deepLinking(true)
	        .validatorUrl(null)
	        .build();
	}
}