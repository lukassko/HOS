package com.app.hos.server.event.source;

import com.app.hos.server.connection.SocketInfo;

public class TcpConnectionEventSource extends TcpEventSource {
	
	private SocketInfo socketInfo;

	public TcpConnectionEventSource(SocketInfo socketInfo) {
		this(socketInfo,null);
	}
	
	public TcpConnectionEventSource(SocketInfo socketInfo,Throwable throwable) {
		super(TcpEventSource.EventSource.CONNECTION,throwable);
		this.socketInfo = socketInfo;
	}

	public SocketInfo getSocketInfo() {
		return this.socketInfo;
	}

	@Override
	public String toString() {
		return "TcpConnectionEventSource [socketInfo=" + socketInfo + (throwable == null ? "" : ", cause=" + throwable) + "]";
	}

}
