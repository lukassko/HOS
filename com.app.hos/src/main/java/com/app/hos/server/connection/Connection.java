package com.app.hos.server.connection;

import java.io.IOException;

import com.app.hos.share.command.builder_v2.Command;

public interface Connection extends Runnable {

	void close();
	
	boolean isOpen();
	
	void send(Command command) throws IOException;
	
	SocketInfo getSocketInfo();
	
	public String getConnectionId();
	
}
