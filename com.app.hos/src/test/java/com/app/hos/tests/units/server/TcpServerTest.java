package com.app.hos.tests.units.server;

import static org.junit.Assert.*;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationEventPublisher;

import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.factory.SocketFactory;
import com.app.hos.server.factory.TcpServer;
import com.app.hos.server.factory.ThreadsExecutor;
import com.app.hos.server.handler.TcpListener;

@RunWith(PowerMockRunner.class)
public class TcpServerTest {

	@Rule
    public MockitoRule rule = MockitoJUnit.rule();
	
	private TcpServer spyServer ;
	
	@Mock
	private ExecutorService executorService;
	
	@Mock
	private ServerSocket serverSocket;
	
	@Mock
	private ConnectionManager connectionManager;
	
	@Mock
	private SocketFactory socketFactory;
	
	@Mock
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Mock
	private TcpListener tcpListener;
	
	@Mock
	private ThreadsExecutor threadsExecutor;
	
	private Thread serverThread;
	
	@Before
	public void startServer() {
		TcpServer tcpServer = new TcpServerBuilder(0)
									.withApplicationEventPublisher(applicationEventPublisher)
									.withConnnectionManager(connectionManager)
									.withSocketFactory(socketFactory)
									.withListener(tcpListener)
									.withThreadsExecutor(threadsExecutor)
									.build();
		
		this.spyServer = Mockito.spy(tcpServer);
		this.spyServer.start();
	}
	
	@After
	public void closeServer() {
		serverThread.interrupt();
	}

	@Test
	public void serverShouldAcceptIncomingConnectionAndAddConnectionToConnectionManagerTest() throws Exception {
		// given
		
			// dependencies
		//doNothing().when(executorService).execute(any());

			// object itself
		//doReturn(executorService).when(tcpServer).getT
		
		// when 
		
		// then
	}
	
}
