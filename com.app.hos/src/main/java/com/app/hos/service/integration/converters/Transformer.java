package com.app.hos.service.integration.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.app.hos.service.integration.TcpEvent;

public class Transformer {

	@Autowired
	public TcpEvent event;
	
	public Message<String> transform(Message<String> msg) {
		Message<String> newMessage = MessageBuilder.withPayload(msg.getPayload())
		        .setHeader(IpHeaders.CONNECTION_ID, event.getConncetionId())
		        .copyHeadersIfAbsent(msg.getHeaders())
		        .build();
		return newMessage;
	}
}
