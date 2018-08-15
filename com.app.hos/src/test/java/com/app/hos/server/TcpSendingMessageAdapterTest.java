package com.app.hos.server;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessageHandlingException;

import static org.mockito.Mockito.*;

import java.io.IOException;

import com.app.hos.server.connection.Connection;
import com.app.hos.server.event.TcpConnectionCorrelationFailedEvent;
import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.factory.Server;
import com.app.hos.server.messaging.IpHeaders;

public class TcpSendingMessageAdapterTest {
	@Rule
    public MockitoRule rule = MockitoJUnit.rule();
	
	private TcpSendingMessageAdapter adapter;

	@Mock
	private Server server;
	
	@Mock
	private ConnectionManager connectionManager;
	
	@Mock
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Before
	public void prepareAdapter() {
		adapter = new TcpSendingMessageAdapter(server);
		adapter.setApplicationEventPublisher(applicationEventPublisher);
		adapter.setConnectionManager(connectionManager);
	}
	
	@Test(expected = MessageHandlingException.class)
	public void sendingMessageWithInvalidConnenectionShouldThrowExceptionAndPublishEvent() throws Exception {
		// given
		Message<?> message = mock(Message.class);
		MessageHeaders messageHeaders = mock(MessageHeaders.class);
		
		when(connectionManager.getConnection(anyString())).thenReturn(null);
		doReturn(messageHeaders).when(message).getHeaders();
		doReturn("connection_id").when(messageHeaders).get(IpHeaders.CONNECTION_ID);
		
		// when
		adapter.handleMessage(message);
		
		// then
		verify(applicationEventPublisher,times(1)).publishEvent(isA(TcpConnectionCorrelationFailedEvent.class));
	}
	
	@Test
	public void sendingMessageWithValidConnenectionShouldSendMessage() throws Exception {
		// given
		Message<?> message = mock(Message.class);
		MessageHeaders messageHeaders = mock(MessageHeaders.class);
		Connection connection = mock(Connection.class);
		
		when(connectionManager.getConnection(anyString())).thenReturn(connection);	
		doReturn(messageHeaders).when(message).getHeaders();
		doReturn("connection_id").when(messageHeaders).get(IpHeaders.CONNECTION_ID);
				
		// when
		adapter.handleMessage(message);
				
		// then
		verify(connection,times(1)).send(message);
	}
	
	@Test
	public void sendingMessageWithValidConnenectionShouldCloseConnectionWhenErrorOccureOndConnectioSendMethod() 
			throws Exception {
		// given
		Message<?> message = mock(Message.class);
		MessageHeaders messageHeaders = mock(MessageHeaders.class);
		Connection connection = mock(Connection.class);
			
		when(connectionManager.getConnection(anyString())).thenReturn(connection);	
		doReturn(messageHeaders).when(message).getHeaders();
		doReturn("connection_id").when(messageHeaders).get(IpHeaders.CONNECTION_ID);
		doThrow(IOException.class).when(connection).send(message);
						
		// when
		adapter.handleMessage(message);
					
		// then
		verify(connection,times(1)).close();
	}

}
