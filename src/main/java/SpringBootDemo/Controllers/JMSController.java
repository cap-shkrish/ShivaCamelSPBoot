package SpringBootDemo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import SpringBootDemo.JMSTemplateDemo.JMSSender;

@RestController
public class JMSController {
	
    @Autowired
    private JMSSender jmsSender;
   
	@GetMapping("/jms")
	public String sayHello()
	{
        String myMessage = "Abdul Rehman Bafaqui Thangal is Sending a JMS Message using the Embedded activeMQ Server.";
        jmsSender.send(myMessage);
        jmsSender.send("johann");
        return myMessage;
    }
}
