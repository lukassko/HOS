package com.app.hos.jdbc.dbcp.pool;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GenericObjectPool<T> implements ObjectPool<T> {

	private int maxIdle;
	
	private int minIdle;
	
	private PooledObjectFactory<T> factory;
	
	private Map<Integer,PooledObject<T>> allObjects;
	
	private AtomicInteger createCount;
	
	private Object monitor;
	
	public GenericObjectPool(PooledObjectFactory<T> factory) {
		this.factory = factory;
		this.maxIdle = 8;
		this.minIdle = 0;
		this.allObjects = new ConcurrentHashMap<>();
		this.createCount = new AtomicInteger();
		this.monitor = new Object();
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public T get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void returnObject(T object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(T object) {
		// TODO Auto-generated method stub

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

}
