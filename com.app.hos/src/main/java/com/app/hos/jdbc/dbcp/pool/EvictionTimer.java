package com.app.hos.jdbc.dbcp.pool;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

class EvictionTimer {

	private static ScheduledThreadPoolExecutor executor;

	private EvictionTimer () {} 
	
	static synchronized void schedule (long delay, long period) {
		if (executor == null) {
			executor = new ScheduledThreadPoolExecutor(1, new EvictionTimer.EvictorThreadFactory());
		}
		//ScheduledFuture<Void> scheduledFuture = executor.scheduleWithFixedDelay(command, delay, period, TimeUnit.MILLISECONDS);
		
	}
	
	private static class EvictorThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r, "commons-pool-evictor-thread");
			
			return t;
		}
		
	}
}
