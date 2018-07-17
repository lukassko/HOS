package com.app.hos.server.config;

import com.app.hos.server.factory.Server;

public final class Tcp {

	public static TcpServerConnectionFactoryBean tcpServer(int port) {
		return new TcpServerConnectionFactoryBean(port);
	}
	
	public static TcpReceivingChannelAdapterFactoryBean tcpServer(Server server) {
		return new TcpReceivingChannelAdapterFactoryBean(server);
	}
}
