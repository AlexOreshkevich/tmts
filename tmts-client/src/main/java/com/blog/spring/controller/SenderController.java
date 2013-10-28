package com.blog.spring.controller;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.blog.spring.jms.TestMessageSender;

@Controller
@RequestMapping("/hello")
public class SenderController {

	static int cntr = 0;

	@Autowired
	private TestMessageSender service;

	@RequestMapping(method = RequestMethod.GET)
	public String printHello(ModelMap model) throws Exception {

		String sendedMessage = "Хуй от SERVLET MESSAGE " + ++cntr;
		model.addAttribute("message", sendedMessage);
		assert service != null;
		doSendMessage(sendedMessage);
		return "hello";
	}

	private void doSendMessage(String _message) throws Exception {
		// Create a ConnectionFactory
		String url = "failover://tcp://192.168.50.31:61616";
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
}
