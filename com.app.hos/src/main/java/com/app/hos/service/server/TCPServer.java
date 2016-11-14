package com.app.hos.service.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.stereotype.Component;

@Component
public class TCPServer {

	private final int TCP_SERVER_PORT = 13020;
	private ServerSocket serverSocket;
	
	public TCPServer() {
		try {
			this.serverSocket = new ServerSocket(TCP_SERVER_PORT);
		} catch (IOException e) {
			System.out.println("SREVR NOT STARTED");
		}
		waitForConnection();
	}
	
	private void waitForConnection() {
	   while (true) {
	       Socket sock = serverSocket.accept();
	       System.out.println("Connected");
	       new Thread(new TCPServerThread(sock)).start();
	    }
	}
}
