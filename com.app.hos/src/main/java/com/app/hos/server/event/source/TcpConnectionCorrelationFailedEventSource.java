package com.app.hos.server.event.source;


public class TcpConnectionCorrelationFailedEventSource extends TcpEventSource {

	private String connectionId;
	
	public TcpConnectionCorrelationFailedEventSource(String connectionId) {
		this(connectionId,null);
	}
	
	public TcpConnectionCorrelationFailedEventSource(String connectionId,Throwable throwable) {
		super(EventSource.CONNECTION,throwable);
		this.connectionId = connectionId;
	}

	public String getConnectionId() {
		return this.connectionId;
	}
	
	@Override
	public String toString() {
		return "TcpConnectionCorrelationFailedEventSource [connectionId=" + this.connectionId + "]";
	}
}
