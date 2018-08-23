package com.app.hos.jdbc.dbcp.pool;

import java.io.Closeable;

public interface ObjectPool<T> extends Closeable {

	T get();
	
	void returnObject(T object);
	
	void add(T object);
	
	void invalidate(T object);
	
	int getActive();
	
	int getIdle();
	
}
