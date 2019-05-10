package SpringBootDemo.Camellama;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class MyFilter {
    public boolean filter(Exchange exchange) {
    	String str = exchange.getMessage().getBody().toString();
        boolean camelArticles = str.contains("=");
        if (camelArticles) {
            System.out.println("CamelArticles " + str);
        }
        System.out.println("CamelArticles " + camelArticles);

        //return camelArticles;
        return false;
    }
}