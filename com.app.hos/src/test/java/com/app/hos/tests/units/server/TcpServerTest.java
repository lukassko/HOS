package com.app.hos.tests.units.server;

import static org.junit.Assert.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.app.hos.server.connection.Connection;
import com.app.hos.server.connection.TcpConnection;
import com.app.hos.server.factory.ConnectionFactory;
import com.app.hos.server.factory.TcpServer;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TcpServer.class)
public class TcpServerTest {

	@Rule
    public MockitoRule rule = MockitoJUnit.rule();
	
	@Spy
	private TcpServer tcpServer = new TcpServer(0);
	
	@Mock
	private ExecutorService executorService;
	
	@Mock
	private ServerSocket serverSocket;
	
	@Mock
	private ConnectionFactory connectionFactory;
	
	private Thread serverThread;
	
	@Before
	public void startServer() {
		tcpServer.setConnnectionFactory(connectionFactory);
		serverThread = new Thread(this.tcpServer);
		serverThread.start();
	}
	
	@After
	public void closeServer() {
		serverThread.interrupt();
	}
	
	// mock private methods which return mocked object instances
	private void mockPrivateMethods() throws Exception {
		when(tcpServer,method(TcpServer.class, "getServerSocket")).withNoArguments().thenReturn(serverSocket);
		when(tcpServer,method(TcpServer.class, "createConnection")).withArguments(any(Socket.class)).thenReturn(createNewConnection());
		when(tcpServer,method(TcpServer.class, "getTaskExecutor")).withNoArguments().thenReturn(executorService);
		PowerMockito.doNothing().when(TcpServer.class,"initializeConnection",Matchers.any(Connection.class));
	}
	
	private Connection createNewConnection() throws SocketException {
		return new TcpConnection.SocketAttributes().socket(new Socket()).build();
	}
	
	@Test
	public void serverShouldAcceptIncomingConnectionAndAddConnecgionToConnectionFactoryTest() throws Exception {
		// given
		mockPrivateMethods();
			// dependencies
		//doNothing().when(executorService).execute(any());

			// object itself
		//doReturn(executorService).when(tcpServer).getT
		
		// when 
		
		// then
	}
	
}
