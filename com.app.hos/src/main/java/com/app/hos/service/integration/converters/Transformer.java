package com.app.hos.service.integration.converters;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;


public class Transformer {
	
	public Message<String> transform(Message<String> msg) {
		System.out.println("Transformer");
		Message<String> newMessage = MessageBuilder.withPayload(msg.getPayload())
		        .copyHeadersIfAbsent(msg.getHeaders())
		        .build();
		return newMessage;
	}
}
