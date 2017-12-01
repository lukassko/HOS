package com.app.hos.tests.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;

public class Utils {

	public static void assertConcurrent(List<Callable<Void>> callables, final int maxTimeoutSeconds) throws InterruptedException {
	    final int numThreads = callables.size();
	    final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
	    final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
	    try {
	        final CountDownLatch allExecutorThreadsReady = new CountDownLatch(numThreads);
	        final CountDownLatch afterInitBlocker = new CountDownLatch(1);
	        final CountDownLatch allDone = new CountDownLatch(numThreads);
	        for (final Callable<?> submittedTestRunnable : callables) {
	            threadPool.submit(new Runnable() {
	                public void run() {
	                    allExecutorThreadsReady.countDown();
	                    try {
	                        afterInitBlocker.await();
	                        submittedTestRunnable.call();
	                    } catch (final Throwable e) {
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
	        										allExecutorThreadsReady.await(callables.size() * 10, TimeUnit.MILLISECONDS));
	        // start all test runners
	        afterInitBlocker.countDown();
	        Assert.assertTrue("Test timeout! More than" + maxTimeoutSeconds + "seconds", allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS));
	    } finally {
	        threadPool.shutdownNow();
	    }
	    Assert.assertTrue("Test failed with exception(s)" + exceptions, exceptions.isEmpty());
	}
}
