package com.app.hos.service.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

public class Transformer {

	@Autowired
	public Event event;
	
	public Message transform(Message msg) {
		System.out.println("Transofrm MESSAGE");
		event.getConncetionId();
		return msg;
	}
}
