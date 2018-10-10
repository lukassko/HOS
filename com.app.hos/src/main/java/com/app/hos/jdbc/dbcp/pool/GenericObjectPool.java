package com.app.hos.jdbc.dbcp.pool;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class GenericObjectPool<T> implements ObjectPool<T> {

	private int maxIdle;
	
	private int minIdle;
	
	private final PooledObjectFactory<T> factory;
	
	private final Map<Integer,PooledObject<T>> allObjects;
	
	private final LinkedBlockingDeque<PooledObject<T>> idleObjects;
	
	private AtomicInteger createCount;
	
	private Object monitor;
	
	private volatile boolean isClosed = false;
	
	public GenericObjectPool(PooledObjectFactory<T> factory) {
		this.factory = factory;
		this.maxIdle = 8;
		this.minIdle = 0;
		this.idleObjects = new LinkedBlockingDeque<>();
		this.allObjects = new ConcurrentHashMap<>();
		this.createCount = new AtomicInteger();
		this.monitor = new Object();
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public T borrowObject() {
		this.assertOpen();
		return null;
	}

	@Override
	public void returnObject(T object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addObject(T object) {
		assertOpen();
		if (this.factory == null) {
			throw new IllegalStateException("Cannot add objects without a factory.");
		}

	}

	@Override
	public void invalidate(T object) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getActive() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIdle() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isClosed() {
		return this.isClosed;
	}
	
	private void assertOpen() {
		if (this.isClosed()) {
			throw new IllegalStateException("Connection pool is closed");
		}
	}
	
	public static class IdentityWrapper<T> {
		private final T instance;
		
		public IdentityWrapper(T instance) {
			this.instance = instance;
		}
		
		public T getObject() {
            return this.instance;
        }
	}
}
