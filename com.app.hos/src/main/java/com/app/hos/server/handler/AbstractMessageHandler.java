package com.app.hos.server.handler;

import org.springframework.context.SmartLifecycle;

public abstract class AbstractMessageHandler implements SmartLifecycle {

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

}
