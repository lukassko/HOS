package com.app.hos.server.config;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.server.factory.Server;
import com.app.hos.server.factory.TcpServer;
import com.app.hos.server.handler.TcpListener;
import com.app.hos.server.messaging.TcpMessageMapper;

public class TcpServerConnectionFactoryBean extends AbstractFactoryBean<Server> {

	private Server server;
	
	public TcpServerConnectionFactoryBean(int port) {
		this.server = new TcpServer(port);
	}

	// set factory properties
	
	public TcpServerConnectionFactoryBean setMapper(TcpMessageMapper mapper) {
		this.server.setMapper(mapper);
		return this;
	}
	
	public TcpServerConnectionFactoryBean setSerializer(Serializer<?> serializer) {
		this.server.setSerializer(serializer);
		return this;
	}
	
	public TcpServerConnectionFactoryBean setDeserializer(Deserializer<?> deserializer) {
		this.server.setDeserializer(deserializer);
		return this;
	}
	
	public TcpServerConnectionFactoryBean setListener(TcpListener listener) {
		this.server.setListener(listener);
		return this;
	}

}
