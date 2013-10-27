package com.blog.spring.jms.camel;

import org.apache.camel.ProducerTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 
 * @author Alex Laputski
 */
public class TestCamelSpring {

	public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext(System.getProperty("user.dir") + "/src/main/webapp/WEB-INF/config/spring-config.xml");
		ProducerTemplate camelTemplate = context.getBean("camelTemplate",
				ProducerTemplate.class);
		camelTemplate.sendBody("jms:queue:TestQueueOne", "Hello Bitch!!!");
	}

}
