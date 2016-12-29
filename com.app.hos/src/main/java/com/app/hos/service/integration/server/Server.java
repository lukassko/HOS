package com.app.hos.service.integration.server;

import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.Message;

public class Server {
	
	public void serverTest(Message<byte[]> message) {
	    String connectionId = message.getHeaders().get(IpHeaders.CONNECTION_ID).toString();
	    System.out.println("CONN "+connectionId);
	}
	

}
