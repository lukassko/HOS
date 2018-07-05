package com.app.hos.server.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;
import org.springframework.messaging.Message;

import com.app.hos.server.TcpMessageMapper;
import com.app.hos.server.TcpListener;

public class TcpConnection implements Connection {

	private Socket socket;

	private TcpListener listener;
	
	private OutputStream socketOutputStream;
	
	private TcpMessageMapper mapper;
	
	private Deserializer<?> deserializer;
	
	private Serializer<?> serializer;
	
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
	public synchronized void send(Message<?>  message) throws IOException {
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
	
	public TcpListener getListener() {
		return listener;
	}

	public void setListener(TcpListener listener) {
		this.listener = listener;
	}

	public TcpMessageMapper getMapper() {
		return mapper;
	}

	public void setMapper(TcpMessageMapper mapper) {
		this.mapper = mapper;
	}

	public Deserializer<?> getDeserializer() {
		return deserializer;
	}

	public void setDeserializer(Deserializer<?> deserializer) {
		this.deserializer = deserializer;
	}

	public Serializer<?> getSerializer() {
		return serializer;
	}

	public void setSerializer(Serializer<?> serializer) {
		this.serializer = serializer;
	}

	@Override
	public String toString() {
		return "TcpConnection ["+ this.getConnectionId() +"]";
	}

}
