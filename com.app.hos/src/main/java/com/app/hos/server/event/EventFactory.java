package com.app.hos.server.event;

@FunctionalInterface
public interface EventFactory {

	default TcpEvent create(Object source){
		return this.create(source, null);
	};
	
	TcpEvent create (Object source, Throwable cause);
	//public TcpEvent create(TcpEventSource source);
	
}
