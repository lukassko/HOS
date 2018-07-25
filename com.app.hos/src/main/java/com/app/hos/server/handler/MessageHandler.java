package com.app.hos.server.handler;

import org.springframework.messaging.Message;

@FunctionalInterface
public interface MessageHandler {

	public void processMessage(Message<?> message);
	
}
