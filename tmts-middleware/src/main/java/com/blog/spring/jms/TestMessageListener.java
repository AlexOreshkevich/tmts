package com.blog.spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.blog.spring.beans.MockDaoService;

/**
 * Class handles incoming messages
 * 
 * @see PointOfIssueMessageEvent
 * @author Alex Laputski
 */
public class TestMessageListener implements MessageListener {

	private static final Logger logger_c = Logger
			.getLogger(TestMessageListener.class);

	@Autowired
	MockDaoService daoService;

	/**
	 * Method implements JMS onMessage and acts as the entry point for messages
	 * consumed by Springs DefaultMessageListenerContainer. When
	 * DefaultMessageListenerContainer picks a message from the queue it invokes
	 * this method with the message payload.
	 */
	public void onMessage(Message message) {
		logger_c.debug("Received message from queue [" + message + "]");

		/* The message must be of type TextMessage */
		if (message instanceof TextMessage) {
			try {
				String msgText = ((TextMessage) message).getText();
				logger_c.debug("About to process message: " + msgText);

				/* call message sender to put message onto second queue */
				daoService.makePersist(message);

			} catch (JMSException jmsEx_p) {
				String errMsg = "An error occurred extracting message";
				logger_c.error(errMsg, jmsEx_p);
			}
		} else {
			String errMsg = "Message is not of expected type TextMessage";
			logger_c.error(errMsg);
			throw new RuntimeException(errMsg);
		}
	}

	public void setDaoService(MockDaoService daoService) {
		this.daoService = daoService;
	}
}
