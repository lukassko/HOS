package com.app.hos.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;

public class MultithreadExecutor {

	public static void assertConcurrent(final List<? extends Runnable> runnables, final int maxTimeoutSeconds) throws InterruptedException {
	    final int numThreads = runnables.size();
	    final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
	    final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
	    try {
	        final CountDownLatch allExecutorThreadsReady = new CountDownLatch(numThreads);
	        final CountDownLatch afterInitBlocker = new CountDownLatch(1);
	        final CountDownLatch allDone = new CountDownLatch(numThreads);
	        for (final Runnable submittedTestRunnable : runnables) {
	            threadPool.submit(new Runnable() {
	                public void run() {
	                    allExecutorThreadsReady.countDown();
	                    try {
	                        afterInitBlocker.await();
	                        submittedTestRunnable.run();
	                    } catch (final Throwable e) {
	                    	e.printStackTrace();
	                        exceptions.add(e);
	                    } finally {
	                        allDone.countDown();
	                    }
	                }
	            });
	        }
	        // wait until all threads are ready
	        Assert.assertTrue("Timeout initializing threads! Perform long lasting initializations "
	        										+ "before passing runnables to assertConcurrent", 
	        										allExecutorThreadsReady.await(runnables.size() * 10, TimeUnit.MILLISECONDS));
	        // start all test runners
	        afterInitBlocker.countDown();
	        Assert.assertTrue("Test timeout! More than" + maxTimeoutSeconds + "seconds", allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS));
	    } finally {
	        threadPool.shutdownNow();
	    }
	    Assert.assertTrue("Test failed with exception(s)" + exceptions, exceptions.isEmpty());
	}

}
