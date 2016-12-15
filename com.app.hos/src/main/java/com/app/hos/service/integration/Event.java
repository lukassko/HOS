package com.app.hos.service.integration;


import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;

public class Event {

	private String connectionId;
	
	public void eventTest (TcpConnectionEvent event) {
		this.connectionId = event.getConnectionId();
		System.out.println("EVENT OCCURE! connId: " + connectionId);
	}
	
	public String getConncetionId () {
		return this.connectionId;
	}
}
