package com.app.hos.server.factory;

public interface ThreadsExecutor {

	void execute(Runnable runnable);
	
	void stop();
}
