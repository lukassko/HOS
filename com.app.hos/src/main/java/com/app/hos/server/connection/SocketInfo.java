package com.app.hos.server.connection;

import java.net.InetAddress;
import java.net.Socket;

public class SocketInfo {

	private int port;
	
	private int localPort;
	
	private InetAddress address;
	
	private InetAddress localAddress;
	
	private String connectionId;
		
	public SocketInfo(String connectionId, Socket socket) {
		this.port = socket.getPort();
		this.localPort = socket.getLocalPort();
		this.address = socket.getInetAddress();
		this.localAddress = socket.getLocalAddress();
		this.connectionId = connectionId;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public int getLocalPort() {
		return this.localPort;
	}
	
	public InetAddress getInetAddress () {
		return this.address;
	}
	
	public InetAddress getLocalAddress () {
		return this.localAddress;
	}

	public String getConnectionId() {
		return connectionId;
	}

}
