package com.app.hos.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;

import com.app.hos.server.connection.Connection;
import com.app.hos.server.event.TcpEvent;
import com.app.hos.server.event.TcpEventFactory;
import com.app.hos.server.factory.Server;
import com.app.hos.server.handler.AbstractMessageHandler;
import com.app.hos.server.messaging.IpHeaders;

public class TcpSendingMessageAdapter extends AbstractMessageHandler {

	private final Logger logger = Logger.getLogger(getClass().getName());
	
	public TcpSendingMessageAdapter(Server server) {
		super(server);
	}
	
	public void handleMessage(Message<?> message) throws MessageHandlingException {
		if (connectionManager == null) {
			logger.severe(this + " No connection manager bound to TcpSendingMessageAdapter");
			return;
		}
		Connection connection = null;
		String connectionId = (String)(message.getHeaders().get(IpHeaders.CONNECTION_ID));
		if (connectionId != null) {
			connection = this.getConnection(connectionId);
		}
		if (connection != null) {
			try {
				connection.send(message);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Error sending message", e);
				connection.close();
			}
		} else {
			MessageHandlingException messageHandlingException = new MessageHandlingException(message,
					"Unable to find outbound socket");
			publishNoConnectionEvent(messageHandlingException,connectionId);
			throw messageHandlingException;
		}
	}
	
	private Connection getConnection(String connectionId) {
		return this.connectionManager.getConnection(connectionId);
	}
	
	@Override
	public void start() {
		if (this.server != null) {
			this.server.start();	
		}
	}

	@Override
	public void stop() {
		if (this.server != null) {
			this.server.stop();	
		}
	}
	
	private void publishNoConnectionEvent(MessageHandlingException messageHandlingException, 
				String connectionId) {
		TcpEvent event = TcpEventFactory.CORRELATION_CONNECTION_EXCEPTION.create(connectionId, messageHandlingException);
		this.applicationEventPublisher.publishEvent(event);
	}

}
