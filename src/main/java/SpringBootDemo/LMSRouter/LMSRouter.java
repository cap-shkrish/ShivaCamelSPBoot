package SpringBootDemo.LMSRouter;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class LMSRouter extends RouteBuilder{
	
	@Autowired
	private MyLMSProcessor myProcessor;

	@Override
	public void configure() throws Exception {
				
		handleException();
		callRouter();	
	}
	
	
	private void handleException()
	{
		onException(Exception.class)
	    .handled(true)
	    .setBody(body())
	    .log("An Error was detected in the JSON format !")
	    .process(new Processor() {
	         public void process(Exchange exchange) throws Exception {
	               Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
	               System.out.println(exception.getLocalizedMessage());
	               System.out.println("Inside Exception Processor \n " + 
	            		   exchange.getIn().getBody());
	         }
	    })
	    .transform().simple("${body}");	
	}
	
	/**
	 *  Router Redirect and call Logic Goes here
	 *  userId = request.getHeader("MemberID");
	 *  activityCode = request.getParameter("activityCode");
	 *  activityType = getActivityType(ebsConnection, activityCode);
	 *  
	 *  if (activityType.equalsIgnoreCase(LmsRouterConstants.ACTIVITY_TYPE_FREE))
	 *  	redirectToOutBoundRouter(request, response, null);
	 *  
	 *  labNumber = request.getParameter("selectedLab")
        getCountOfUserOrgAssoc() // gets the list of labs the user is associated with org contact relationship in TCA
 		// gets the list of labs the user is associated with org contact relationship in TCA 
        if(activityType.equalsIgnoreCase(LmsRouterConstants.ACTIVITY_TYPE_KITCAPPURCHASE
        			&& getCountOfUserOrgAssoc() == 0)){
                 redirectToEStore(response);
                  
             findLabsHavingKits(request)    
             claimKit(myFirstKey.toString(), response, request);
             
             if (activityType.equalsIgnoreCase(LmsRouterConstants.ACTIVITY_TYPE_KITCAP))
             redirectToEStore(response);
	 *  	if (activityType.equalsIgnoreCase(LmsRouterConstants.ACTIVITY_TYPE_1M))
	 *  		claimKit(myFirstKey.toString(), response, request)
	 *  
			if (activityType.equalsIgnoreCase(LmsRouterConstants.ACTIVITY_TYPE_PURCHASE)) 
			redirectToEStore(response)
			
	 *  
	 */
	
	private void callRouter()
	{
		String routeFromId = "direct:lmsRouter";
		Predicate p1 = body().contains("address");
		Predicate p2 = jsonpath("$.activity[?(@.activityType=='ACTIVITY_TYPE_FREE')]");
		Predicate cond = PredicateBuilder.and(p1, p2);

		from(routeFromId).routeId(routeFromId)
		.log("I'm in the Camel MyCamelChoiceRouter!")
			.choice()
				.when(cond)
					.log("I'm in .when().jsonpath(\"$.address[?(@.state=='Germany')]\")!")
					.process(myProcessor)
					.marshal().json(JsonLibrary.Jackson)
					.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
					.enrich("https://s40y5e5lt8.execute-api.us-east-1.amazonaws.com/person/dynamodb")
				.otherwise().log("I'm in Otherwise direct:multi as State is NOT GERMANY!!")
				
					.to("direct:eStore")
			.end()
			.transform().simple("${body}");
		
	}

}

@Component
class MyLMSProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		System.out.println("\nInside configure in MyProcessor \n ");
		System.out.println("exchange.getMessage().getBody(): " + exchange.getMessage().getBody());

		//exchange.getIn().setBody(std);
		//exchange.setOut(exchange.getIn());
	}

}
