package com.app.hos.jdbc.dbcp.pool;

import java.io.Closeable;

public interface ObjectPool<T> extends Closeable {

	T borrowObject() throws Exception;
	
	void returnObject(T object) throws Exception;
	
	void addObject(T object) throws Exception;
	
	void invalidateObject(T object) throws Exception;
	
	int getActive();
	
	int getIdle();
	
}
