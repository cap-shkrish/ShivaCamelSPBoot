package SpringBootDemo.JMSTemplateDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JMSSender {

    private static Logger log = LoggerFactory.getLogger(JMSSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String myMessage) {
        log.info("JmsTemplate sending with convertAndSend() to queue < " + myMessage + " >");
        jmsTemplate.convertAndSend("HelloWorld.QUEUE", myMessage);
    }
    
}