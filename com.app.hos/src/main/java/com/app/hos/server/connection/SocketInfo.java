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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((connectionId == null) ? 0 : connectionId.hashCode());
		result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + ((localAddress == null) ? 0 : localAddress.hashCode());
		result = prime * result + localPort;
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SocketInfo other = (SocketInfo) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (connectionId == null) {
			if (other.connectionId != null)
				return false;
		} else if (!connectionId.equals(other.connectionId))
			return false;
		if (hostName == null) {
			if (other.hostName != null)
				return false;
		} else if (!hostName.equals(other.hostName))
			return false;
		if (localAddress == null) {
			if (other.localAddress != null)
				return false;
		} else if (!localAddress.equals(other.localAddress))
			return false;
		if (localPort != other.localPort)
			return false;
		if (port != other.port)
			return false;
		return true;
	}

}
