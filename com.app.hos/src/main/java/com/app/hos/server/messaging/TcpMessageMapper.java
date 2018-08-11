package com.app.hos.server.messaging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.Message;

import com.app.hos.server.connection.Connection;
import com.app.hos.server.connection.SocketInfo;

public class TcpMessageMapper implements InboundMessageMapper<Connection>,
										 OutboundMessageMapper<Object> {

	@Override
	public Message<?> toMessage(Connection connection) throws Exception {
		SocketInfo socketInfo = connection.getSocketInfo();
		Object payload = connection.getPayload();

		return MessageBuilder.withPayload(payload)
				.setMessageHeaders(getStandardHeaders(socketInfo))
		        .build();
	}

	private Map<String, Object> getStandardHeaders(SocketInfo socketInfo) {
		Map<String, Object> headers = new HashMap<>();
		headers.put(IpHeaders.HOSTNAME, socketInfo.getHostName());
		headers.put(IpHeaders.IP_ADDRESS, socketInfo.getInetAddress());
		headers.put(IpHeaders.REMOTE_PORT, socketInfo.getPort());
		headers.put(IpHeaders.CONNECTION_ID, socketInfo.getConnectionId());
		return headers;
	}
	
	@Override
	public Object fromMessage(Message<?> message) throws Exception {
		return message.getPayload();
	}
}
