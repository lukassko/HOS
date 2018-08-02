package com.app.hos.tests.units.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
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
		startServerThreadWith(noConcurrency());
		
		// then
		verify(applicationEventPublisher,times(1)).publishEvent(isA(TcpOpenConnectionEvent.class));
		verify(connectionManager,times(1)).createConnection(isA(Socket.class));
		verify(threadsExecutor,times(1)).execute(isA(TcpConnection.class));
	}
	
	@Test
	public void whenStopServerDuringAcceptingNewConnectionServerShouldCloseAlreadyCreatedSocketAndCloseAllConnections() throws IOException {
		// given
		initMocks();
		
		// when
		// do answer and stop when accepting connection
		
		// then
		// check if socket.close() was called
	}
	
	@Test
	public void whenStopServerDuringConnectionInitializationServerShouldThrowExcecption() {
		// given

		// when

		// then
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
	
	private List<Runnable> noConcurrency() {
		return new LinkedList<>();
	}
}
