package com.app.hos.server.connection;

import java.net.InetAddress;
import java.net.Socket;

public class SocketInfo {

	private final int port;
	
	private final int localPort;
	
	private final InetAddress address;
	
	private final InetAddress localAddress;
	
	private final String connectionId;
		
	private final String hostName;
	
	public SocketInfo(String connectionId, Socket socket) {
		this.port = socket.getPort();
		this.localPort = socket.getLocalPort();
		this.address = socket.getInetAddress();
		this.localAddress = socket.getLocalAddress();
		this.hostName = address.getHostName();
		this.connectionId = connectionId;
	}
	
	public String getHostName() {
		return this.hostName;
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

	@Override
	public String toString() {
		return "SocketInfo [port=" + port + ", localPort=" + localPort + ", address=" + address + ", localAddress="
				+ localAddress + ", connectionId=" + connectionId + "]";
	}

	
}
