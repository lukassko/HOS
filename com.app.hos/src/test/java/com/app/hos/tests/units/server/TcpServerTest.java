package com.app.hos.tests.units.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import org.springframework.context.ApplicationEventPublisher;

import com.app.hos.server.connection.TcpConnection;
import com.app.hos.server.event.TcpOpenConnectionEvent;
import com.app.hos.server.event.TcpServerExceptionEvent;
import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.factory.SocketFactory;
import com.app.hos.server.factory.TcpServer;
import com.app.hos.server.factory.TcpThreadsExecutor;
import com.app.hos.server.handler.TcpListener;
import com.app.hos.tests.utils.MultithreadExecutor;

public class TcpServerTest {

	@Rule
    public MockitoRule rule = MockitoJUnit.rule();
	
	private TcpServer tcpServer ;

	@Mock
	private ServerSocket mockedServerSocket;
	
	@Mock
	private ConnectionManager connectionManager;
	
	@Mock
	private SocketFactory socketFactory;
	
	@Mock
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Mock
	private TcpListener tcpListener;
	
	@Mock
	private TcpThreadsExecutor threadsExecutor;
	
	
	@Before
	public void prepareServer() throws IOException {
		this.tcpServer = new TcpServerBuilder(0)
									.withApplicationEventPublisher(applicationEventPublisher)
									.withConnnectionManager(connectionManager)
									.withSocketFactory(socketFactory)
									.withListener(tcpListener)
									.withThreadsExecutor(threadsExecutor)
									.build();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void serverShouldAcceptIncomingConnectionAndAddConnectionToConnectionManagerAndExecuteConnectionThread() throws IOException {
		// given
		initMocks();
		Socket mockedSocket = mock(Socket.class);
		when(mockedServerSocket.accept()).thenReturn(mockedSocket).thenThrow(IOException.class); // throw exception (second iteration will end server thread)
		
		// when 
		startServerThreadWith(noOtherConcurrency());
		
		// then
		verify(applicationEventPublisher,times(1)).publishEvent(isA(TcpOpenConnectionEvent.class));
		verify(connectionManager,times(1)).createConnection(isA(Socket.class));
		verify(threadsExecutor,times(1)).execute(isA(TcpConnection.class));
	}
	
	@Test
	public void whenStopServerDuringAcceptingNewConnectionServerShouldCloseAlreadyCreatedSocketAndCloseAllConnections() throws IOException {
		// given
		initMocks();
		Socket mockedSocket = mock(Socket.class);

		doAnswer(new Answer<Socket>() {
		    public Socket answer(InvocationOnMock invocation) throws InterruptedException {
		    	tcpServer.stop();
		        return mockedSocket;
		     }
		 }).when(mockedServerSocket).accept();
		
		// when
		startServerThreadWith(noOtherConcurrency());
		
		// then
		verify(mockedSocket,times(1)).close();
		verify(applicationEventPublisher,times(1)).publishEvent(isA(TcpServerExceptionEvent.class));
		verify(connectionManager,times(0)).createConnection(isA(Socket.class));
		verify(connectionManager,times(1)).closeConnections();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void whenStopServerDuringConnectionInitializationServerShouldThrowExcecption() throws IOException {
		// given
		initMocks();
		Socket mockedSocket = mock(Socket.class);
		when(mockedServerSocket.accept()).thenReturn(mockedSocket).thenThrow(IOException.class); // throw exception (second iteration will end server thread)

		doAnswer(new Answer<Void>() {
		    public Void answer(InvocationOnMock invocation) throws InterruptedException {
		    	tcpServer.stop();
		        return null;
		     }
		 }).when(threadsExecutor).execute(any());
		
		// when
		startServerThreadWith(noOtherConcurrency());
		
		// then
		verify(applicationEventPublisher,times(1)).publishEvent(isA(TcpOpenConnectionEvent.class));
		verify(applicationEventPublisher,times(1)).publishEvent(isA(TcpServerExceptionEvent.class));
		verify(connectionManager,times(1)).closeConnections();
	}
	
	private void initMocks() throws IOException {
		TcpConnection mockedConnection = mock(TcpConnection.class);	
		when(socketFactory.getServerSocket(anyInt())).thenReturn(mockedServerSocket);
		when(connectionManager.createConnection(any(Socket.class))).thenReturn(mockedConnection);
	}

	private void startServerThreadWith(List<Runnable> threads) {
		
		List<Runnable> runnables = new LinkedList<>();
		runnables.add(tcpServer);
		runnables.addAll(threads);
		
		doAnswer(new Answer<Void>() {
		    public Void answer(InvocationOnMock invocation) throws InterruptedException {
		    	MultithreadExecutor.assertConcurrent(runnables, 10);
		        return null;
		     }
		 }).when(threadsExecutor).execute(tcpServer);
		
		
		tcpServer.start();
	}
	
	private List<Runnable> noOtherConcurrency() {
		return new LinkedList<>();
	}
}
