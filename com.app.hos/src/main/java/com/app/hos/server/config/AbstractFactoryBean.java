package com.app.hos.server.config;

import org.springframework.beans.factory.FactoryBean;

public abstract class AbstractFactoryBean<T> implements FactoryBean<T> {
	
	protected volatile T target; 
	
	public final T get() {
		return this.target;
	}
	
	@Override
	public T getObject() throws Exception {
		return get();
	}

	@Override
	public Class<?> getObjectType() {
		return get().getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
