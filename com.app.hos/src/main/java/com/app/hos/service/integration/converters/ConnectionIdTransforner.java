package com.app.hos.service.integration.converters;

import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.app.hos.share.command.builder_v2.Command;

public class ConnectionIdTransforner {
	
	private String connectionId;
	
	public void setConnectionId (String connectionId) {
		this.connectionId = connectionId;
	}
	
	public Message<Command> transform(Message<Command> commandMsg) {
		return MessageBuilder.withPayload(commandMsg.getPayload())
		        .setHeader(IpHeaders.CONNECTION_ID, connectionId)
		        .copyHeadersIfAbsent(commandMsg.getHeaders())
		        .build();
	}
}
