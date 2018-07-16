package com.app.hos.server.event;

import com.app.hos.server.event.source.TcpEventSource;

public interface EventFactory {

	public TcpEvent create(TcpEventSource source);
	
}
