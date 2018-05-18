package com.app.hos.service.integration.server;

import org.springframework.messaging.Message;

import com.app.hos.share.command.builder_v2.Command;

public interface Gateway {
	
	public void send(Message<Command> message);

}
