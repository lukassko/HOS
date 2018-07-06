package com.app.hos.server.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

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
	
	public TcpConnection(SocketAttributes socketAttributes) {
		this.socket = socketAttributes.socket;
	}
	
	private InputStream inputStream() throws IOException {
		return this.socket.getInputStream();
	}
	
	@Override
	public void close() {
		try {
			this.socket.close();
		} catch (IOException e) {
		}
	}

	@Override
	public boolean isOpen() {
		return !this.socket.isClosed();
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
	public Object getPayload() throws IOException {
		return this.deserializer.deserialize(inputStream());
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

	public static class SocketAttributes {
		
		private Socket socket;
		
		private int receiveBufferSize;
		
		private int sendBufferSize;
		
		private int timeout = -1;
		
		private boolean keepAlive;

		public SocketAttributes socket(Socket socket) {
			this.socket = socket;
			return this;
		}

		public SocketAttributes receiveBufferSize(int receiveBufferSize) {
			this.receiveBufferSize = receiveBufferSize;
			return this;
		}

		public SocketAttributes sendBufferSize(int sendBufferSize) {
			this.sendBufferSize = sendBufferSize;
			return this;
		}

		public SocketAttributes keepAlive(boolean keepAlive) {
			this.keepAlive = keepAlive;
			return this;
		}
		
		public SocketAttributes keepAlive(int timeout) {
			this.timeout = timeout;
			return this;
		}

		public TcpConnection build () throws SocketException {
			if(sendBufferSize >= 0) 
				this.socket.setSoTimeout(timeout);
			if(receiveBufferSize > 0) 
				this.socket.setReceiveBufferSize(receiveBufferSize);
			if(sendBufferSize > 0) 
				this.socket.setSendBufferSize(sendBufferSize);
			socket.setKeepAlive(this.keepAlive);
			return new TcpConnection(this);
		}
	}

}
