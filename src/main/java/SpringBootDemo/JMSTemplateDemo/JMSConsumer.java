package SpringBootDemo.JMSTemplateDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import SpringBootDemo.Beans.Person;
import SpringBootDemo.Services.MybatisDemoService;
import SpringBootDemo.Services.ResultsSetMapper;

import javax.jms.Session;

@Component
public class JMSConsumer {

    private static Logger log = LoggerFactory.getLogger(JMSConsumer.class);

    @JmsListener(destination = "HelloWorld.QUEUE")
    public void receiveMessage(@Payload String myMessage,
                               @Headers MessageHeaders headers,
                               Message message, Session session) {
        log.info("\nJMSConsumer received < " + myMessage + " >\n");

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######          Message Details           #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("headers: " + headers);
        log.info("message: " + message);
        log.info("session: " + session);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        
                
		if(myMessage.equals("johann"))
	        mybatisDemoService.selectPersonSql(myMessage);
		
    }
    
    @Autowired
    private MybatisDemoService mybatisDemoService;
}