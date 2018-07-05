package com.app.hos.server.event;

import com.app.hos.server.connection.Connection;

public enum TcpEventType implements EventFactory {

	//OPEN_CONNECTION((connection) -> new TcpOpenConnectionEvent(connection));
	
	OPEN_CONNECTION(new EventFactory() {
		@Override
		public TcpEvent create(Connection connection) {
			return new TcpOpenConnectionEvent(connection);
		}
	});
	
	private TcpEventType(EventFactory factory) {
		this.factory = factory;
	}
	
	private EventFactory factory;
	
	@Override
	public TcpEvent create(Connection connection) {
		return factory.create(connection);
	}
}
