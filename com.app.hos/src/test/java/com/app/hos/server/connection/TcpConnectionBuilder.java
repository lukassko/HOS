package com.app.hos.server.connection;

import java.net.Socket;
import java.net.SocketException;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.handler.TcpListener;
import com.app.hos.server.messaging.TcpMessageMapper;

public class TcpConnectionBuilder {

	private TcpConnection connection;

	public TcpConnectionBuilder (Socket socket) throws SocketException {
		this.connection = new TcpConnection.ConnectionBuilder().socket(socket).build();
	}
	
	public TcpConnectionBuilder withConnnectionManager(ConnectionManager connectionManager) {
		this.connection.setConnectionManager(connectionManager);
		return this;
	}
	
	public TcpConnectionBuilder withApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.connection.setApplicationEventPublisher(applicationEventPublisher);
		return this;
	}
	
	public TcpConnectionBuilder withMessageMapper(TcpMessageMapper mapper) {
		this.connection.setMapper(mapper);
		return this;
	}
	
	public TcpConnectionBuilder withListener(TcpListener listener) {
		this.connection.setListener(listener);
		return this;
	}
	
	public TcpConnectionBuilder withSerializer(Serializer<?> serializer) {
		this.connection.setSerializer(serializer);
		return this;
	}
	
	public TcpConnectionBuilder withDesrializer(Deserializer<?> deserializer) {
		this.connection.setDeserializer(deserializer);
		return this;
	}
	
	public TcpConnection build() {
		return this.connection;
	}
}
