package com.app.hos.server.connection;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.handler.TcpListener;
import com.app.hos.server.messaging.TcpMessageMapper;

public class TcpConnectionTest {

	@Rule
    public MockitoRule rule = MockitoJUnit.rule();
	
	private TcpConnection tcpConnection ;

	@Mock
	private Socket mockedSocket;
	
	@Mock
	private ConnectionManager connectionManager;
	
	@Mock
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Mock
	private TcpListener tcpListener;
	
	@Mock
	private TcpMessageMapper mapper;
	
	@Mock
	private volatile Deserializer<?> deserializer;
	
	@Mock
	private volatile Serializer<?> serializer;
	
	@Before
	public void prepareServer() throws IOException {
		this.tcpConnection = new TcpConnectionBuilder(mockedSocket)
									.withApplicationEventPublisher(applicationEventPublisher)
									.withConnnectionManager(connectionManager)
									.withListener(tcpListener)
									.withSerializer(serializer)
									.withDesrializer(deserializer)
									.withMessageMapper(mapper)
									.build();
	}

	@Test
	public void whenReceivedMessageOnSocketTheListenerShouldBeNotify() {
		// given
		
		// when

		// then
	}
	
	@Test
	public void whenReceivedMessageOnSocketWhichCauseExceptionTheConnectionShouldBeClose() {
		// given
		
		// when

		// then
	}
	
	@Test
	public void whenSocketBlockOnReadingAndConnectionIsClosedTheSocketCloseEventShouldBeSend() {
		// given
		
		// when

		// then
	}
	
	@Test
	public void whenSocketBlockOnReadingAndSendingMessageTheOutpuStreamShouldBeFlushed() {
		// given
		
		// when

		// then
	}
	
	@Test
	public void whenSocketBlockOnReadingAndSendingMessageCauseExceptionTheConnectionShouldBeClose() {
		// given
		
		// when

		// then
	}
	
	@Test
	public void whenSocketBlockOnReadingAndSendingMessageDuringOtherThreadCloseConnectionCauseExceptionTheConnectionShouldBeClose() {
		// given
		
		// when

		// then
	}
}
