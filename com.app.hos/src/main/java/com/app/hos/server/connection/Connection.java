package com.app.hos.server.connection;

import java.io.IOException;

import org.springframework.messaging.Message;

public interface Connection extends Runnable {

	void close();
	
	boolean isOpen();
	
	void send(Message<?> message) throws IOException;
	
	public SocketInfo getSocketInfo();
	
	public String getConnectionId();
	
	public Object getPayload() throws IOException;
	
}
