package com.app.hos.service.integration;


import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;

public class TcpEvent {

	private String connectionId;
	
	public void eventTest (TcpConnectionEvent event) {
		this.connectionId = event.getConnectionId();
		System.out.println("!!!!!! EVENT !!!!!!");
		System.out.println("SOURCE: " + event.getSource().toString());
		System.out.println("FACTORY NAME: " + event.getConnectionFactoryName());
		System.out.println("CONN_ID: " + connectionId);
	}
	
	public String getConncetionId () {
		return this.connectionId;
	}
}
