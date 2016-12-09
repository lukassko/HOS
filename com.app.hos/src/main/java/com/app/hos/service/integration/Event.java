package com.app.hos.service.integration;


import org.springframework.integration.ip.tcp.connection.TcpConnectionEvent;

public class Event {

	public void eventTest (TcpConnectionEvent event) {
		System.out.println("EVENT OCCURE!");

	}
}
