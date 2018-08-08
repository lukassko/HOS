package com.app.hos.server.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.serializer.Serializer;
import org.springframework.messaging.Message;

import static org.mockito.Mockito.*;

import com.app.hos.common.MultithreadExecutor;
import com.app.hos.server.event.TcpConnectionExceptionEvent;
import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.handler.TcpListener;
import com.app.hos.server.messaging.TcpMessageMapper;

public class TcpConnectionTest {

	private TcpConnection tcpConnection;

	@Rule
    public MockitoRule rule = MockitoJUnit.rule();
	
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
	private Serializer<?> serializer;

	@Before
	public void initConnection() throws IOException {
		initMockedSocket();
		
		this.tcpConnection = new TcpConnectionBuilder(mockedSocket)
				.withApplicationEventPublisher(applicationEventPublisher)
				.withConnnectionManager(connectionManager)
				.withListener(tcpListener)
				.withSerializer(serializer)
				.withMessageMapper(mapper)
				.build();
	}

	private void initMockedSocket() throws UnknownHostException {
		InetAddress inetAddressMocked = mock(InetAddress.class);
		when(mockedSocket.getInetAddress()).thenReturn(inetAddressMocked);
		when(mockedSocket.getPort()).thenReturn(5655);
		when(mockedSocket.getLocalAddress()).thenReturn(inetAddressMocked);
		when(mockedSocket.getLocalPort()).thenReturn(2323);
		when(inetAddressMocked.getHostName()).thenReturn("MockHostName");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void whenReceivedMessageOnSocketTheListenerShouldBeNotify() throws Exception {
		// given
		Message<?> mockedMessage = mock(Message.class);
		// second call should throw exception to end connection thread
		doReturn(mockedMessage).doThrow(IOException.class).when(mapper).toMessage(tcpConnection);

		// when
		startConnectionThreadWith(noOtherConcurrency());
		
		// then
		verify(tcpListener,times(1)).onMessage(mockedMessage);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void whenReceivedMessageOnSocketWhichCauseExceptionTheConnectionShouldBeClose() throws Exception {
		// given
		when(mapper.toMessage(tcpConnection)).thenThrow(IOException.class);

		// when
		startConnectionThreadWith(noOtherConcurrency());
				
		// then
		verify(connectionManager,times(1)).removeConnection(any());
		verify(applicationEventPublisher,times(1)).publishEvent(isA(TcpConnectionExceptionEvent.class));
	}
	
	//@Test
	public void whenSendingMessageWithoutErrorsTheOutpuStreamShouldBeFlushed() {
		// given
		
		// when

		// then
	}
	
	//@Test
	public void whenSendingMessageCauseExceptionTheConnectionShouldBeClose() {
		// given
		
		// when

		// then
	}
	
	//@Test
	public void whenSocketBlockOnReadingAndSendingMessageDuringOtherThreadCloseConnectionCauseExceptionTheConnectionShouldBeClose() {
		// given
		
		// when

		// then
	}
	
	private void startConnectionThreadWith(List<Runnable> threads) throws InterruptedException {
		List<Runnable> runnables = new LinkedList<>();
		runnables.add(tcpConnection);
		runnables.addAll(threads);
		MultithreadExecutor.assertConcurrent(runnables, 3);
	}
	
	private List<Runnable> concurrent(Runnable ... runnable) {
		List<Runnable> runnables = new LinkedList<>();
		for (Runnable r : runnable) {
			runnables.add(r);
		}
		return runnables;
	}
	
	private List<Runnable> noOtherConcurrency() {
		return new LinkedList<>();
	}
}
