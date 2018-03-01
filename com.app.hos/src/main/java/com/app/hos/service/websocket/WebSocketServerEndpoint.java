package com.app.hos.service.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.app.hos.service.exceptions.WebSocketException;
import com.app.hos.service.exceptions.handler.ExceptionUtils;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.utils.json.JsonConverter;

//decoders = WebCommandDecoder.class, encoders = WebCommandEncoder.class,
@ServerEndpoint(value = "/websocket", configurator = SpringConfigurator.class)
public class WebSocketServerEndpoint {

	private WebSocketManager webSocketManager;

	private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	@Autowired
	public WebSocketServerEndpoint(WebSocketManager manager) {
		this.webSocketManager = manager;
	}

	@OnOpen
	public void onOpen(Session session) throws IOException {
		sessions.add(session);
	}

	@OnMessage
	public void onMessage(Session session, String message) {
		webSocketManager.executeCommand((s, c) -> sendMessage(s, c), session, message);
	}

	@OnClose
	public void onClose(Session session) {
		sessions.remove(session);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		ExceptionUtils.handle(new WebSocketException(session, throwable));
	}

	public synchronized void broadcastMessage(String message) {
		sessions.forEach(session -> {
			sendMessage(session, message);
		});
	}

	public void sendMessage(Session session, WebCommand command) {
		try {
			sendMessage(session,JsonConverter.getJson(command));
		} catch (IOException e) {
			ExceptionUtils.handle(e);
		} 
	}
	
	public synchronized void sendMessage(Session session, String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			ExceptionUtils.handle(e);
		}
	}
	
}
