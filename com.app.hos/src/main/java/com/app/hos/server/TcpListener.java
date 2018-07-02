package com.app.hos.server;

@FunctionalInterface
public interface TcpListener {

	void onMessage(byte [] payload);
}
