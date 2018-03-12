package com.app.hos.service;

public interface AbstractMapFactory {

	public void get(Object key);
	
	public void add(Object key, Object value);
	
	public void register(String path);
}
