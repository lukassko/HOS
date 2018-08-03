package com.app.hos.server.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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

import static org.mockito.Mockito.*;

import org.springframework.context.ApplicationEventPublisher;

import com.app.hos.common.MultithreadExecutor;
import com.app.hos.server.connection.TcpConnection;
import com.app.hos.server.event.TcpOpenConnectionEvent;
import com.app.hos.server.event.TcpServerExceptionEvent;
import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.factory.SocketFactory;
import com.app.hos.server.factory.TcpServer;
import com.app.hos.server.factory.TcpThreadsExecutor;
import com.app.hos.server.handler.TcpListener;

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
		final Socket mockedSocket = mock(Socket.class);
		final CountDownLatch waitToCallAccpet = new CountDownLatch(1);
		
		Runnable stopServerRunnable = stopServerConcurrent(waitToCallAccpet);
		
		doAnswer(new Answer<Socket>() {
		    public Socket answer(InvocationOnMock invocation) throws InterruptedException {
		    	waitToCallAccpet.countDown();
		        return mockedSocket;
		     }
		 }).when(mockedServerSocket).accept();
		
		// when
		startServerThreadWith(concurrent(stopServerRunnable));
		
		// then
		verify(mockedSocket,times(1)).close();
		verify(applicationEventPublisher,times(1)).publishEvent(isA(TcpServerExceptionEvent.class));
		verify(mockedServerSocket,times(1)).accept();
		verify(connectionManager,times(1)).closeConnections();
	}
	
	@Test
	public void whenStopServerDuringConnectionInitializationServerShouldThrowExcecption() throws IOException {
		// given
		initMocks();
		Socket mockedSocket = mock(Socket.class);
		
		final CountDownLatch waitToCall = new CountDownLatch(1);
		
		Runnable stopServerRunnable = stopServerConcurrent(waitToCall);
	
		when(mockedServerSocket.accept()).thenReturn(mockedSocket); 

		doAnswer(new Answer<Void>() {
		    public Void answer(InvocationOnMock invocation) throws InterruptedException {
		    	waitToCall.countDown();
		        return null;
		     }
		 }).when(threadsExecutor).execute(any());
		
		// when
		startServerThreadWith(concurrent(stopServerRunnable));
		
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
	
	private Runnable stopServerConcurrent(CountDownLatch countDownLatch) {
		Runnable stopServerRunnable = () -> {
			try {
				countDownLatch.await(2, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
			} finally {
				tcpServer.stop();
			}
		};
		return stopServerRunnable;
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
