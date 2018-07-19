package com.app.hos.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.messaging.Message;

import com.app.hos.server.connection.Connection;
import com.app.hos.server.factory.ConnectionFactory;
import com.app.hos.server.handler.AbstractMessageHandler;
import com.app.hos.server.messaging.IpHeaders;

public class TcpSendingMessageAdapter extends AbstractMessageHandler {

	private final Logger logger = Logger.getLogger(getClass().getName());
	
	private ConnectionFactory connectionFactory;

	public void handleMessage(Message<?> message) {
		if (connectionFactory == null) {
			// throw
		}
		Connection connection = null;
		String connectionId = (String)message.getHeaders().get(IpHeaders.CONNECTION_ID);
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
			// publish no connection event
			// throw exception
		}
	}
	
	private Connection getConnection(String connectionId) {
		return this.connectionFactory.getConnection(connectionId);
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
