package com.app.hos.service.server;

import java.net.Socket;

import com.app.hos.entity.Gateway;

public class TCPServerThread implements Runnable {

	private Socket clientSocket;
	private Gateway gateway;
	
	public TCPServerThread (Socket clientSocekt){
		this.clientSocket = clientSocekt;
	}
	
	public void run() {

	}

}
