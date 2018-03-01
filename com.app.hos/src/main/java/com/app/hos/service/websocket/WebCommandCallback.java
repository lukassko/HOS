package com.app.hos.service.websocket;

import javax.websocket.Session;

import com.app.hos.service.websocket.command.builder.WebCommand;

@FunctionalInterface
public interface WebCommandCallback {

	public void onReady(Session session, WebCommand command);
}
