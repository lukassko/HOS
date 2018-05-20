package com.app.hos.service;

@FunctionalInterface
public interface SimpleFactory <T> {
	
	public T create(String message);
	
}
