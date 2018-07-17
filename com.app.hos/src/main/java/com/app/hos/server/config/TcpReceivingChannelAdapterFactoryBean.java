package com.app.hos.server.config;

import com.app.hos.server.TcpReceivingMessageHandler;
import com.app.hos.server.factory.Server;

public class TcpReceivingChannelAdapterFactoryBean extends AbstractFactoryBean<TcpReceivingMessageHandler> {
	
	private final TcpReceivingMessageHandler channelAdapter;
	
	public TcpReceivingChannelAdapterFactoryBean(Server server) {
		this.channelAdapter = new TcpReceivingMessageHandler();
		this.channelAdapter.setConnectionFactory(server);
	}

}
