package com.app.hos.server.connection;

import java.io.IOException;
import java.net.Socket;

import com.app.hos.share.command.builder_v2.Command;

public class TcpConnection implements Connection {

	private Socket socket;

	public TcpConnection(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void send(Command command) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SocketInfo getSocketInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConnectionId() {
		StringBuilder connctionidBuilder = new StringBuilder();
		connctionidBuilder.append(socket.getInetAddress().toString()+":");
		connctionidBuilder.append(socket.getPort()+"-");
		connctionidBuilder.append(socket.getLocalPort()+":");
		connctionidBuilder.append(socket.getPort());
		return connctionidBuilder.toString();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	
	
	
	@Override
	public String toString() {
		return "TcpConnection ["+ this.getConnectionId() +"]";
	}

}
