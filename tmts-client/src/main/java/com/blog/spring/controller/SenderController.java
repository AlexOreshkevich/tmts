package com.blog.spring.controller;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blog.spring.jms.TestMessageSender;

@Controller
public class SenderController {

	static int cntr = 0;

	private static final Logger logger_c = Logger
			.getLogger(SenderController.class);

	@Autowired
	private TestMessageSender service;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String printHello(ModelMap model) {

		logger_c.info("Access printHello...");

		try {
			String sendedMessage = "Hello от SERVLET MESSAGE " + ++cntr;
			model.addAttribute("message", sendedMessage);
			assert service != null;
			doSendMessage(sendedMessage);
		} catch (Exception e) {
			logger_c.error("FATAL printHello", e);
			model.addAttribute("message", e.getMessage());
		}
		return "hello";
	}

	private void doSendMessage(String _message) throws Exception {
		// Create a ConnectionFactory
		String url = "failover://tcp://54.246.235.163:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"admin", "admin", url);
		// Create a Connection
		Connection connection = connectionFactory.createConnection();
		connection.start();
		// Create a Session
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		// Create the destination
		Destination destination = session.createQueue("TestQueueOne");
		// Create a MessageProducer from the Session to the Queue
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		// Create a messages
		TextMessage message = session.createTextMessage(_message);
		producer.send(message);
		session.close();
		connection.close();
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "test";
	}
}
