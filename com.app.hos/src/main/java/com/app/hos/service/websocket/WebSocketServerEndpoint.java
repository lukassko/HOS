package com.app.hos.service.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.app.hos.service.websocket.command.WebCommandFactory;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.tests.integrations.manager.WebCommandManagerIT;
import com.app.hos.utils.json.JsonConverter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

//decoders = WebCommandDecoder.class, encoders = WebCommandEncoder.class,
@ServerEndpoint(value = "/websocket",configurator = SpringConfigurator.class)
public class WebSocketServerEndpoint {
	
	private WebSocketManager manager;
	
	@Autowired
	public WebSocketServerEndpoint (WebSocketManager manager) {
		this.manager = manager;
	}
	
	private Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
    public void onOpen(Session session) throws IOException {
		sessions.add(session);
    }
 
    @OnMessage
    public void onMessage(Session session, String message) {
    	manager.executeCommand(new WebCommandCallback() {
    		
			@Override
			public void onReady(Session session, String message) {
				sendMessage(session, message);
			}
			
		}, session, message);
    }
 
    @OnClose
    public void onClose(Session session) throws IOException {
    	System.out.println("Connection Close");
    	sessions.remove(session);
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) {
    	System.out.println("Error with Session: " + session.getId() + " | throwable " + throwable.getMessage());
    }
    
    public synchronized void broadcastMessage(String message) {
    	sessions.forEach(session -> {
	        	sendMessage(session,message);
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
