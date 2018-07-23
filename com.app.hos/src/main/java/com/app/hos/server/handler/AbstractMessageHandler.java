package com.app.hos.server.handler;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.SmartLifecycle;

public abstract class AbstractMessageHandler implements SmartLifecycle {

	protected ApplicationEventPublisher applicationEventPublisher;
	
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
}
