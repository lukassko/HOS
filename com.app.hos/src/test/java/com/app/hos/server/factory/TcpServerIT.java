package com.app.hos.server.factory;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.*;

import com.app.hos.server.connection.Connection;
import com.app.hos.server.event.TcpEvent;
import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.factory.SocketFactory;
import com.app.hos.server.factory.TcpServer;
import com.app.hos.server.handler.TcpListener;
import com.app.hos.server.handler.TcpThreadsExecutor;
import com.app.hos.server.messaging.TcpMessageMapper;

public class TcpServerIT {

	@Rule
    public MockitoRule rule = MockitoJUnit.rule();
	
	private final int PORT = 12040;
	
	private TcpServer tcpServer;
	
	private ConnectionManager connectionManager = new TcpConnectionManager();
	
	private SocketFactory socketFactory = new TcpSocketFactory();
	
	private TcpThreadsExecutor threadsExecutor = new TcpThreadsExecutor();
	
	private TcpMessageMapper messageMapper = new TcpMessageMapper();
	
	@Mock
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Mock
	private TcpListener tcpListener;

	@Before
	public void prepareServer() throws IOException {
		this.tcpServer = new TcpServerBuilder(PORT)
									.withApplicationEventPublisher(applicationEventPublisher)
									.withConnnectionManager(connectionManager)
									.withSocketFactory(socketFactory)
									.withListener(tcpListener)
									.withThreadsExecutor(threadsExecutor)
									.withMessageMapper(messageMapper)
									.build();
		tcpServer.start();
	}

	@After
	public void stopServer() {
		this.tcpServer.stop();
	}
	
	@Test
	public void serverShouldAcceptIncomingConnectionAndCloseConnectionWhenServerStop() throws Exception {
		Socket clientSocket = null;
		try {
			// initialize count down latches
			final CountDownLatch finished = new CountDownLatch(4);
			final CountDownLatch afterOpenConnection = new CountDownLatch(1); 
			doAnswer(new Answer<Object>() {
					
				public Object answer(InvocationOnMock invocation) throws Throwable {
					afterOpenConnection.countDown();
					finished.countDown();
				    return null;
				}
					
			}).when(applicationEventPublisher).publishEvent(isA(TcpEvent.class));
			
			ArgumentCaptor<TcpEvent> eventCaptor = ArgumentCaptor.forClass(TcpEvent.class);
			
			// create socket connecting to server
			clientSocket = new Socket("localhost", PORT); 
			
			// wait to publish event with new connection
			afterOpenConnection.await(1, TimeUnit.SECONDS);
			
			// verify opened connection
			List<Connection> connections = new ArrayList<>(connectionManager.getConnections());
			assertFalse(connections.isEmpty());
			Connection connection = connections.get(0);
			String connectionId = connection.getConnectionId();
			assertTrue(connection.isOpen());
			assertNotNull(connectionManager.getConnection(connectionId));

			// stop server
			tcpServer.stop();
			
			// wait to publish all expected events
			finished.await(1, TimeUnit.SECONDS);

			// verify published events
			verify(applicationEventPublisher, times(4)).publishEvent(eventCaptor.capture());
						
			// verify that no opened connections are available after server stop
			connections = new ArrayList<>(connectionManager.getConnections());
			assertTrue(connections.isEmpty());
			assertNull(connectionManager.getConnection(connectionId));
			
		} finally {
			clientSocket.close();
		}
	}
}
