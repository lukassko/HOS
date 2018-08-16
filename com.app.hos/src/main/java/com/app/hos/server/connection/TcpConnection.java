package com.app.hos.server.connection;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;
import org.springframework.messaging.Message;

import com.app.hos.server.event.TcpEvent;
import com.app.hos.server.event.TcpEventFactory;
import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.handler.TcpListener;
import com.app.hos.server.messaging.TcpMessageMapper;

public class TcpConnection implements Connection {

	private final Logger logger = Logger.getLogger(getClass().getName());
	
	private volatile ApplicationEventPublisher applicationEventPublisher;
	
	private final Socket socket;

	private volatile TcpListener listener;
	
	private volatile OutputStream socketOutputStream;
	
	private volatile TcpMessageMapper mapper;
	
	private volatile Deserializer<?> deserializer;
	
	private volatile Serializer<?> serializer;
	
	private volatile ConnectionManager connectionManager;
	
	private final SocketInfo socketInfo;
	
	private final String connectionId;
	
	private TcpConnection(ConnectionBuilder socketAttributes) {
		this.socket = socketAttributes.socket;
		this.connectionId = getConnectionId(this.socket);
		this.socketInfo = new SocketInfo(this.connectionId, this.socket);
	}
	
	private InputStream inputStream() throws IOException {
		return this.socket.getInputStream();
	}

	@Override
	public void close() {
		if(isOpen()) {
			try {
				this.socket.close(); // this cause SocketException when InputStream is blocked for reading
				// this.socket.shutdownInput();
				publishCloseConnectionEvent();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	@Override
	public boolean isOpen() {
		return !this.socket.isClosed();
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void send(Message<?>  message) throws Exception {
		if (this.socketOutputStream == null) {
			this.socketOutputStream = new BufferedOutputStream(this.socket.getOutputStream());
		}
		Object object = this.mapper.fromMessage(message);
		try {
			((Serializer<Object>)serializer).serialize(object, this.socketOutputStream);
			this.socketOutputStream.flush();
		} catch(Exception e) {
			publishConnectionExceptionEvent(e);
			removeConnection();
			throw e;
		}
	}
	
	private void removeConnection() {
		close();
		connectionManager.closeConnection(this.connectionId);
	}

	private void publishCloseConnectionEvent() {
		TcpEvent event = TcpEventFactory.CLOSE_CONNECTION.create(this.socketInfo);
		doPublishEvent(event);
	}
		
	private void publishConnectionExceptionEvent(Throwable cause) {
		TcpEvent event = TcpEventFactory.CONNECTION_EXCEPTION.create(this.socketInfo,cause);
		doPublishEvent(event);
	}
	
	@Override
	public void run() {
		boolean run = true;
		while(run) {
			Message<?> message = null;
			try {
				message = this.mapper.toMessage(this);
			} catch (Exception e) {
				publishConnectionExceptionEvent(e);
				removeConnection();
				run = false;
			}
			if (run && message != null) {
				if (this.listener == null) {
					logger.severe(this + " No TcpListener registered with connection: " + this.connectionId + 
							" - Received message " + message);
				}
				listener.onMessage(message);
			}
		}
	}

	@Override
	public SocketInfo getSocketInfo() {
		return this.socketInfo;
	}

	@Override
	public Object getPayload() throws IOException {
		return this.deserializer.deserialize(inputStream());
	}
	
	@Override
	public String getConnectionId() {
		return this.connectionId;
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
	
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionFactory) {
		this.connectionManager = connectionFactory;
	}

	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}


	private void doPublishEvent(TcpEvent event) {
		if (this.applicationEventPublisher == null) {
			logger.severe(this + "No ApplicationEventPublisher associated with the connection " + this.connectionId);
		} else {
			System.out.println("doPublishEvent " + event.toString());
			this.applicationEventPublisher.publishEvent(event);
		}
	}
	
	private String getConnectionId(Socket socket) {
		StringBuilder connctionidBuilder = new StringBuilder();
		connctionidBuilder.append(socket.getInetAddress()+":");
		connctionidBuilder.append(socket.getPort()+"-");
		connctionidBuilder.append(socket.getLocalAddress()+":");
		connctionidBuilder.append(socket.getLocalPort());
		return connctionidBuilder.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((socketInfo == null) ? 0 : socketInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TcpConnection other = (TcpConnection) obj;
		if (socketInfo == null) {
			if (other.socketInfo != null)
				return false;
		} else if (!socketInfo.equals(other.socketInfo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TcpConnection ["+ this.getConnectionId() +"]";
	}
	
	public static class ConnectionBuilder {
		
		private Socket socket;
		
		private int receiveBufferSize;
		
		private int sendBufferSize;
		
		private int timeout;
		
		private boolean keepAlive;

		public ConnectionBuilder socket(Socket socket) {
			this.socket = socket;
			return this;
		}

		public ConnectionBuilder receiveBufferSize(int receiveBufferSize) {
			this.receiveBufferSize = receiveBufferSize;
			return this;
		}

		public ConnectionBuilder sendBufferSize(int sendBufferSize) {
			this.sendBufferSize = sendBufferSize;
			return this;
		}

		public ConnectionBuilder keepAlive(boolean keepAlive) {
			this.keepAlive = keepAlive;
			return this;
		}
		
		public ConnectionBuilder keepAlive(int timeout) {
			this.timeout = timeout;
			return this;
		}

		public TcpConnection build () throws SocketException {
			if(sendBufferSize > 0) 
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
