package com.app.hos.server.config;

import com.app.hos.server.TcpReceivingMessageAdapter;
import com.app.hos.server.factory.Server;

public class TcpReceivingChannelAdapterFactoryBean extends AbstractFactoryBean<TcpReceivingMessageAdapter> {
	
	public TcpReceivingChannelAdapterFactoryBean(Server server) {
		this.target = new TcpReceivingMessageAdapter();
		this.target.setConnectionFactory(server);
	}

}
