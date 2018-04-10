package com.app.hos.service;

public interface AbstractMapFactory {

	public Object get(Object key);
	
	public void add(Object key, Object value);
	
	public void register(String path);
}
