package com.app.hos.server;

import org.springframework.messaging.Message;

@FunctionalInterface
public interface TcpListener {

	void onMessage(Message<?> message);
}
