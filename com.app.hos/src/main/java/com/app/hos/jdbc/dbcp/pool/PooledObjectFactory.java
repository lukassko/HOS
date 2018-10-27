package com.app.hos.jdbc.dbcp.pool;

public interface PooledObjectFactory <T> {

	PooledObject<T> makeObject() throws Exception;
	
	void destroyObject(PooledObject<T> object) throws Exception;
	
	void passivateObject(PooledObject<T> object) throws Exception;
	
	void activateObject(PooledObject<T> object) throws Exception;
	
	void validateObject(PooledObject<T> object);
}
