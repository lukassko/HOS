package com.app.hos.server.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TcpThreadsExecutor implements ThreadsExecutor {

	private volatile ExecutorService connectionExecutor;
	
	private Object monitor = new Object();
	
	// double check locking pattern
	private ExecutorService getTaskExecutor() {
		if (this.connectionExecutor == null) {
			synchronized(this.monitor) {
				if (this.connectionExecutor == null) {
					this.connectionExecutor = Executors.newCachedThreadPool();
				}
			}
		}
		return this.connectionExecutor;
	}
	
	@Override
	public void execute(Runnable runnable) {
		getTaskExecutor().execute(runnable);
	}
	
	@Override
	public void stop() {
		synchronized(this.monitor) {
			connectionExecutor.shutdown();
			try {
				if (!connectionExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
					connectionExecutor.shutdownNow();
					connectionExecutor.awaitTermination(10, TimeUnit.SECONDS);
				}
			} catch (InterruptedException e) {
				connectionExecutor.shutdownNow();
			} finally {
				this.connectionExecutor = null;
			}
		}
	}
}
