package com.app.hos.service.websocket;

import javax.websocket.Session;

@FunctionalInterface
public interface WebCommandCallback {

	public void onReady(Session session, String message);
}
