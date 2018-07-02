package com.app.hos.server.connection;

import java.net.InetAddress;
import java.net.Socket;

public class SocketInfo {

	private final Socket socket;
	
	public SocketInfo(Socket socket) {
		this.socket = socket;	
	}
	
	public int getPort() {
		return this.socket.getPort();
	}
	
	public int getLocalPort() {
		return this.socket.getLocalPort();
	}
	
	public InetAddress getInetAddress () {
		return this.socket.getInetAddress();
	}
	
	public InetAddress getLocalAddress () {
		return this.socket.getLocalAddress();
	}
	
	public boolean isConnected() {
		return this.socket.isConnected();
	}

}
