package com.app.hos.service.integration.converters;

import java.io.IOException;

import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.connection.TcpConnection;
import org.springframework.integration.ip.tcp.connection.TcpMessageMapper;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.app.hos.share.command.builder.Command;

public class IpTcpMessageMapper extends TcpMessageMapper {

	@Override
	public Message<?> toMessage(TcpConnection connection) throws Exception {
		Message<?> message = MessageBuilder.withPayload(connection.getPayload())
				.setHeader(IpHeaders.HOSTNAME, connection.getHostName())
		        .setHeader(IpHeaders.IP_ADDRESS, connection.getHostAddress())
		        .setHeader(IpHeaders.REMOTE_PORT, connection.getPort())
		        .setHeader(IpHeaders.CONNECTION_ID, connection.getConnectionId())
		        .build();
		deserialize(message.getPayload());
		return message;
	}
	
	private void deserialize(Object data) throws IOException, ClassNotFoundException {
	    Command cmd = (Command)data;
	}
}
