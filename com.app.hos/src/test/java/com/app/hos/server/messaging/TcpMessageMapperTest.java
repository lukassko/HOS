package com.app.hos.server.messaging;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import static org.mockito.Mockito.*;

import java.net.InetAddress;

import com.app.hos.server.connection.Connection;
import com.app.hos.server.connection.SocketInfo;
import com.app.hos.utils.Utils;

public class TcpMessageMapperTest {

	private TcpMessageMapper messageMapper = new TcpMessageMapper();
		
	@Test
	public void passingConnectionToMessageMapperShouldReturnProperMessagePayload() throws Exception {
		// given
		Connection connection = mock(Connection.class);
		SocketInfo socketInfo = mock(SocketInfo.class);
		Object payload = new Object();
		InetAddress inetAddress = mock(InetAddress.class);
		when(connection.getSocketInfo()).thenReturn(socketInfo);
		when(connection.getPayload()).thenReturn(payload);
		when(socketInfo.getHostName()).thenReturn("HOST_NAME");
		when(socketInfo.getInetAddress()).thenReturn(inetAddress);
		when(socketInfo.getPort()).thenReturn(Utils.generateRandomInteger());
		when(socketInfo.getConnectionId()).thenReturn("CONNECTION_ID");
		
		// when
		Message<?> message = messageMapper.toMessage(connection);
		
		// then
		Object messagePayload = message.getPayload();
		
		assertTrue(messagePayload == payload);
	}
	
	@Test
	public void gettingPayloadFromMessageShouldReturnProperObject() throws Exception {
		// given
		Object messagePayload = new Object();
		Message<Object> message = new GenericMessage<>(messagePayload);
		
		// when
		Object receivedMessagePayload = messageMapper.fromMessage(message);
		
		// then
		assertTrue(receivedMessagePayload == messagePayload);
	}
}

