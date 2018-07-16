package com.app.hos.server.event.source;

import com.app.hos.server.factory.TcpServerListener;

public class TcpServerEventSource extends TcpEventSource {

	private TcpServerListener server;
	
	public TcpServerEventSource(TcpServerListener server) {
		this(server,null);
	}
	
	public TcpServerEventSource(TcpServerListener server,Throwable throwable) {
		super(EventSource.SERVER,throwable);
		this.server = server;
	}

	public TcpServerListener getServer() {
		return this.server;
	}
	
	@Override
	public String toString() {
		return "TcpServerEventSource [server=" + server + (throwable == null ? "" : ", cause=" + throwable) + "]";
	}
}
