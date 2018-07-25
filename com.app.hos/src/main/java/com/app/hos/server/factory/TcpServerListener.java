package com.app.hos.server.factory;

import java.net.SocketAddress;

public interface TcpServerListener {

	public int getPort();
	
	public SocketAddress getSocketAddress();

	public boolean isListening();
}
