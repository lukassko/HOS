package com.app.hos.jdbc.dbcp.pool;

public interface PooledObjectFactory <T> {

	PooledObject<T> makeObject() throws Exception;
	
	void destryObject(PooledObject<T> object) throws Exception;
}
