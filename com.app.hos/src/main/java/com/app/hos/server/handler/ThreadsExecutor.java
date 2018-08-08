package com.app.hos.server.handler;

public interface ThreadsExecutor {

	void execute(Runnable runnable);
	
	void stop();
}
