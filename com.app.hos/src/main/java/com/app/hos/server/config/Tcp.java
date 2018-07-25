package com.app.hos.server.config;

import com.app.hos.server.factory.ConnectionFactory;
import com.app.hos.server.factory.Server;

public final class Tcp {

	public static TcpServerConnectionFactoryBean tcpServer(int port) {
		return new TcpServerConnectionFactoryBean(port);
	}
	
	public static TcpReceivingChannelAdapterFactoryBean receivingAdapter(Server server) {
		return new TcpReceivingChannelAdapterFactoryBean(server);
	}
	
	public static TcpSendingChannelAdapterFactoryBean sendingAdapter(ConnectionFactory connetionFacotry) {
		return new TcpSendingChannelAdapterFactoryBean(connetionFacotry);
	}
}
