package com.app.hos.server.handler;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.SmartLifecycle;

import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.factory.Server;

public abstract class AbstractMessageHandler implements SmartLifecycle {

	protected ApplicationEventPublisher applicationEventPublisher;
	
	protected Server server;
	
	protected ConnectionManager connectionFacotry;
	
	private volatile boolean running;

	@Override
	public boolean isRunning() {
		return this.running;
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		return false;
	}

	@Override
	public void stop(Runnable callback) {
		// TODO Auto-generated method stub
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	public void setConnectionFactory(ConnectionManager connectionFacotry) {
		this.connectionFacotry = connectionFacotry;
	}
}
