package com.app.hos.server;

import java.util.logging.Logger;

import org.springframework.messaging.Message;

import com.app.hos.server.factory.Server;
import com.app.hos.server.handler.AbstractMessageHandler;
import com.app.hos.server.handler.MessageHandler;
import com.app.hos.server.handler.TcpListener;

public class TcpReceivingMessageAdapter extends AbstractMessageHandler implements TcpListener {


	private final Logger logger = Logger.getLogger(getClass().getName());
	
	private MessageHandler messageHandler;
	
	public TcpReceivingMessageAdapter(Server server) {
		super(server);
	}
	
	@Override
	public void onMessage(Message<?> message) {
		if (messageHandler == null) {
			logger.severe(this + " No MessageHandler bound to TcpReceivingMessageAdapter");
			return;
		}
		messageHandler.processMessage(message);
	}

	public void setConnectionFactory(Server server) {
		server.setListener(this);
	}
	
	@Override
	public boolean isAutoStartup() {
		return true;
	}
	
	@Override
	public void start() {
		if (this.server != null) {
			this.server.setListener(this);
			this.server.start();
		}
	}

	@Override
	public void stop() {
		if (this.server != null) {
			this.server.stop();
		}
	}
	
	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}
}
