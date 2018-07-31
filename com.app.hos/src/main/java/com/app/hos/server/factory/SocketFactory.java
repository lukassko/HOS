package com.app.hos.server.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public interface SocketFactory {

	ServerSocket getServerSocket (int port) throws IOException;
	
	ServerSocket getServerSocket (int port, int backlog) throws IOException;
	
	Socket getSocket() throws IOException;
	
}
