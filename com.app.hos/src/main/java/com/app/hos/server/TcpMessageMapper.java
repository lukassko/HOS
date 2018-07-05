package com.app.hos.server;

import org.springframework.integration.mapping.InboundMessageMapper;
import org.springframework.integration.mapping.OutboundMessageMapper;
import org.springframework.messaging.Message;

import com.app.hos.server.connection.TcpConnection;

public class TcpMessageMapper implements 
										InboundMessageMapper<TcpConnection>,
										OutboundMessageMapper<Object> {

	@Override
	public Message<?> toMessage(TcpConnection tcpConnection) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fromMessage(Message<?> message) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
