package com.app.hos.server.messaging;

import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.app.hos.server.connection.Connection;
import com.app.hos.server.connection.SocketInfo;

public class TcpMessageMapper implements InboundMessageMapper<Connection>,
										 OutboundMessageMapper<Object> {

	@Override
	public Message<?> toMessage(Connection connection) throws Exception {
		SocketInfo socketInfo = connection.getSocketInfo();
		Object payload = connection.getPayload();
		
		MessageHeaders messageHeaders = new MessageHeaders(null);
		
		addStandardHeaders(socketInfo, messageHeaders);

		return MessageBuilder.withPayload(payload)
				.setMessageHeaders(messageHeaders)
		        .build();
	}

	private void addStandardHeaders(SocketInfo socketInfo, MessageHeaders messageHeaders) {
		messageHeaders.put(IpHeaders.HOSTNAME, socketInfo.getHostName());
		messageHeaders.put(IpHeaders.IP_ADDRESS, socketInfo.getInetAddress());
		messageHeaders.put(IpHeaders.REMOTE_PORT, socketInfo.getPort());
		messageHeaders.put(IpHeaders.CONNECTION_ID, socketInfo.getConnectionId());
	}
	
	@Override
	public Object fromMessage(Message<?> message) throws Exception {
		return message.getPayload();
	}
}
