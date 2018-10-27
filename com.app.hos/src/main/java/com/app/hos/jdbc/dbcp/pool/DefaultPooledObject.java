package com.app.hos.jdbc.dbcp.pool;

public class DefaultPooledObject<T> implements PooledObject<T> {

	private final T object;
	
	private final long createTime;
	
	private PooledObjectState state;
	
	public DefaultPooledObject(T object) {
		this.object = object;
		this.state = PooledObjectState.IDLE;
		this.createTime = System.currentTimeMillis();
	}
	
	@Override
	public int compareTo(T o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public T getObject() {
		return this.object;
	}

	@Override
	public void invalidate() {
		this.state = PooledObjectState.INVALID;
	}

	@Override
	public void allocate() {
		if (this.state == PooledObjectState.IDLE) {
			this.state = PooledObjectState.ALLOCATED;
		}
	}

	@Override
	public void deallocate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub
	}

	@Override
	public long getCreateTime() {
		return this.getCreateTime();
	}

	@Override
	public synchronized void markReturning() {
		this.state = PooledObjectState.RETURNING;
	}
}
