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

//decoders = WebCommandDecoder.class, encoders = WebCommandEncoder.class,
@ServerEndpoint(value = "/websocket", configurator = SpringConfigurator.class)
public class WebSocketServerEndpoint {

	private WebSocketManager webSocketManager;

	private Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	@Autowired
	public WebSocketServerEndpoint(WebSocketManager manager) {
		this.webSocketManager = manager;
	}

	@OnOpen
	public void onOpen(Session session) throws IOException {
		System.out.println("SOCKET OPEN");
		sessions.add(session);
	}

	@OnMessage
	public void onMessage(Session session, String message) {
		webSocketManager.executeCommand((s, m) -> sendMessage(s, m), session, message);
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		System.out.println("SOCKET onClose");
		sessions.remove(session);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
	}

	public synchronized void broadcastMessage(String message) {
		sessions.forEach(session -> {
			sendMessage(session, message);
		});
	}

	public synchronized void sendMessage(Session session, String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
