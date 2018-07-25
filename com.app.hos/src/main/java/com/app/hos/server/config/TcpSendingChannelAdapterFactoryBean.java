package com.app.hos.server.config;

import com.app.hos.server.TcpSendingMessageAdapter;
import com.app.hos.server.factory.ConnectionFactory;

public class TcpSendingChannelAdapterFactoryBean extends AbstractFactoryBean<TcpSendingMessageAdapter> {

	public TcpSendingChannelAdapterFactoryBean(ConnectionFactory connetionFacotry) {
		this.target = new TcpSendingMessageAdapter();
	}

}
