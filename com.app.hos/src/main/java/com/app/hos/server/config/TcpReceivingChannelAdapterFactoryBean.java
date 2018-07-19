package com.app.hos.server.config;

import com.app.hos.server.TcpReceivingMessageAdapter;
import com.app.hos.server.factory.Server;

public class TcpReceivingChannelAdapterFactoryBean extends AbstractFactoryBean<TcpReceivingMessageAdapter> {
	
	private final TcpReceivingMessageAdapter channelAdapter;
	
	public TcpReceivingChannelAdapterFactoryBean(Server server) {
		this.channelAdapter = new TcpReceivingMessageAdapter();
		this.channelAdapter.setConnectionFactory(server);
	}

}
