package com.app.hos.server.config;

import com.app.hos.server.TcpSendingMessageAdapter;
import com.app.hos.server.factory.ConnectionManager;

public class TcpSendingChannelAdapterFactoryBean extends AbstractFactoryBean<TcpSendingMessageAdapter> {

	public TcpSendingChannelAdapterFactoryBean(ConnectionManager connetionFacotry) {
		this.target = new TcpSendingMessageAdapter();
	}

}
