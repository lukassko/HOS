package com.app.hos.service;

public interface AbstractMapFactory<K,V,T> {

	public T get(K key);
	
	public void add(K key, V value);
	
	public void register(String path);
}
