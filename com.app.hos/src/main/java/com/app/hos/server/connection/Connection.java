package com.app.hos.server.connection;

import java.io.IOException;

import org.springframework.messaging.Message;

public interface Connection extends Runnable {

	void close();
	
	boolean isOpen();
	
	void send(Message<?>  message) throws IOException;
	
	SocketInfo getSocketInfo();
	
	public String getConnectionId();
	
}
