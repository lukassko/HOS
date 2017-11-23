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

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.service.websocket.decoders.WebCommandDecoder;
import com.app.hos.service.websocket.decoders.WebCommandEncoder;

@ServerEndpoint(value = "/websocket",decoders = WebCommandDecoder.class, encoders = WebCommandEncoder.class)
public class WebSocketServerEndpoint {
	
	public WebSocketServerEndpoint () {
		System.out.println("CREATE @ServerEndpoint");
	}
	
	private Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
    public void onOpen(Session session) throws IOException {
		System.out.println("New connaction Open");
		sessions.add(session);
    }
 
    @OnMessage
    public void onMessage(Session session, WebCommand message) throws IOException {
        // Handle new messages
    	WebCommand cmd = new WebCommand();
    	cmd.setMessage("ELO");
    	cmd.setType(WebCommandType.GET_ALL_DEVICES);
    	sendMessage(session,cmd);
    }
 
    @OnClose
    public void onClose(Session session) throws IOException {
    	System.out.println("Connection Close");
    	sessions.remove(session);
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
    
    public synchronized void broadcastMessage(WebCommand command) {
    	sessions.forEach(session -> {
	        try {
	        	session.getBasicRemote().sendObject(command);
	        } catch (IOException | EncodeException e) {
	        	e.printStackTrace();
	        }
    	});
    }
    
    public synchronized void sendMessage(Session session, WebCommand command) {
    	try {
			session.getBasicRemote().sendObject(command);
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		} 
    }
}
