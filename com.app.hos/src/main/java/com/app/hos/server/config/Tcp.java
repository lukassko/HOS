package com.app.hos.server.config;

import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.factory.Server;

public final class Tcp {

	public static TcpServerConnectionFactoryBean tcpServer(int port) {
		return new TcpServerConnectionFactoryBean(port);
	}
	
	public static TcpReceivingChannelAdapterFactoryBean receivingAdapter(Server server) {
		return new TcpReceivingChannelAdapterFactoryBean(server);
	}
	
	public static TcpSendingChannelAdapterFactoryBean sendingAdapter(Server server,ConnectionManager connectionManager) {
		return new TcpSendingChannelAdapterFactoryBean(server,connectionManager);
	}
}
