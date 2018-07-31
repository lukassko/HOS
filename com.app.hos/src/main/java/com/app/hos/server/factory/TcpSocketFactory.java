package com.app.hos.server.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

public class TcpSocketFactory implements SocketFactory {

	@Override
	public ServerSocket getServerSocket(int port) throws IOException {
		return ServerSocketFactory.getDefault().createServerSocket(port);
	}
	
	@Override
	public ServerSocket getServerSocket(int port, int backlog) throws IOException {
		return ServerSocketFactory.getDefault().createServerSocket(port,backlog);
	}

	@Override
	public Socket getSocket() throws IOException {
		throw new UnsupportedOperationException("Get net sokcet facotory method not supported");
	}

}
