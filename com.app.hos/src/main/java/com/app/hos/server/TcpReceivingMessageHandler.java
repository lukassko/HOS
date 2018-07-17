package com.app.hos.server;

import org.springframework.context.SmartLifecycle;
import org.springframework.messaging.Message;

import com.app.hos.server.factory.Server;

public class TcpReceivingMessageHandler implements TcpListener, SmartLifecycle {

	@Override
	public void onMessage(Message<?> message) {
		// TODO Auto-generated method stub
	}

	public void setConnectionFactory(Server server) {
		server.setListener(this);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPhase() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stop(Runnable callback) {
		// TODO Auto-generated method stub
		
	}
}
