package com.app.hos.server.config;

import com.app.hos.server.TcpSendingMessageAdapter;
import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.factory.Server;

public class TcpSendingChannelAdapterFactoryBean extends AbstractFactoryBean<TcpSendingMessageAdapter> {

	public TcpSendingChannelAdapterFactoryBean(Server server, ConnectionManager connectionManager) {
		this.target = new TcpSendingMessageAdapter(server);
		target.setConnectionManager(connectionManager);
	}

}
