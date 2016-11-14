package com.app.hos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hos.service.server.TCPServer;

@Service
public class HOSService {
	
	@Autowired
	private TCPServer tcpServer;
	
	public HOSService(){
		
	}
	
}
