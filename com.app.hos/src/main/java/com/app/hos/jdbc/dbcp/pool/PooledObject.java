package com.app.hos.jdbc.dbcp.pool;

public interface PooledObject<T> extends Comparable<T>{

	T getObject();
	
	void invalidate();
	
	void allocate();
	
	void deallocate();
	
	void use();
	
	long getCreateTime();
	
	void markReturning();
}
