package com.app.hos.server;

import org.springframework.messaging.Message;

import com.app.hos.server.factory.Server;
import com.app.hos.server.handler.AbstractMessageHandler;
import com.app.hos.server.handler.TcpListener;

public class TcpReceivingMessageAdapter extends AbstractMessageHandler implements TcpListener {

	@Override
	public void onMessage(Message<?> message) {
		// TODO Auto-generated method stub
	}

	public void setConnectionFactory(Server server) {
		server.setListener(this);
	}
	
	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}
}
