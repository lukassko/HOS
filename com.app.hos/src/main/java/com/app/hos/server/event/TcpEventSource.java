package com.app.hos.server.event;

import com.app.hos.server.connection.SocketInfo;
import com.app.hos.server.factory.TcpServerListener;

public class TcpEventSource {

	public enum EventSource {
		CONNECTION, SERVER;
	}
	
	private SocketInfo socketInfo;
	
	private Throwable throwable;
	
	private TcpServerListener server;
	
	private EventSource source;
	
	public TcpEventSource(SocketInfo socketInfo) {
		this(socketInfo,null);
	}
	
	public TcpEventSource(SocketInfo socketInfo,Throwable throwable) {
		this.socketInfo = socketInfo;
		this.throwable = throwable;
		this.source = EventSource.CONNECTION;
	}
	
	public TcpEventSource(TcpServerListener server) {
		this(server,null);
	}
	
	public TcpEventSource(TcpServerListener server,Throwable throwable) {
		this.server = server;
		this.throwable = throwable;
		this.source = EventSource.SERVER;
	}

	public SocketInfo getSocketInfo() {
		return this.socketInfo;
	}

	public Throwable getThrowable() {
		return this.throwable;
	}

	public TcpServerListener getServer() {
		return this.server;
	}

	public EventSource getSource() {
		return this.source;
	}

}
