package com.app.hos.server.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.app.hos.server.connection.Connection;
import com.app.hos.utils.Utils;

public class TcpConnectionManagerTest {

	private TcpConnectionManager connectionManager = new TcpConnectionManager();
	
	@Rule
    public MockitoRule rule = MockitoJUnit.rule();
	
	@Test
	public void afterCreateNewConnectionManagerShouldBeAbleToFindAndReturnAddedConnection() throws Exception {
		// given
		Socket socket = mock(Socket.class);
		initMockedSocket(socket);

		// when
		Connection connection = connectionManager.createConnection(socket);
		
		// then
		assertEquals(connection, connectionManager.getConnection(connection.getConnectionId()));
	}
	
	@Test
	public void afterAddAndRemoveNewConnectionManagerShouldNotFindAddedConnection() throws Exception {
		// given
		Socket socket = mock(Socket.class);
		initMockedSocket(socket);

		// when
		Connection connection = connectionManager.createConnection(socket);
		String connectionId = connection.getConnectionId();
		connectionManager.closeConnection(connectionId);
		
		// then
		assertNull(connectionManager.getConnection(connectionId));
	}
	
	@Test
	public void afterAddAndCloseConnectionManagerShouldNotFindAddedConnectionAndSocketShouldBeClose() throws Exception {
		Socket socket = mock(Socket.class);
		initMockedSocket(socket);

		// when
		Connection connection = connectionManager.createConnection(socket);
		String connectionId = connection.getConnectionId();
		connectionManager.closeConnection(connectionId);

		// then
		assertNull(connectionManager.getConnection(connectionId));
		verify(socket,times(1)).close();
	}
	
	@Test
	public void closeConnectionsShouldRemoveAllConnectionsAndCloseSockets() throws Exception {
		Socket socket1 = mock(Socket.class);
		initMockedSocket(socket1);

		Socket socket2 = mock(Socket.class);
		initMockedSocket(socket2);
		
		// when
		Connection connection1 = connectionManager.createConnection(socket1);
		Connection connection2 = connectionManager.createConnection(socket2);
		
		String connectionId1 = connection1.getConnectionId();
		String connectionId2 = connection2.getConnectionId();
		connectionManager.closeConnections();

		// then
		assertNull(connectionManager.getConnection(connectionId1));
		assertNull(connectionManager.getConnection(connectionId2));
		verify(socket1,times(1)).close();
		verify(socket2,times(1)).close();
	}
	
	private void initMockedSocket(Socket socket) throws UnknownHostException {
		InetAddress inetAddressMocked = mock(InetAddress.class);
		when(socket.getInetAddress()).thenReturn(inetAddressMocked);
		when(socket.getPort()).thenReturn(Utils.generateRandomInteger());
		when(socket.getLocalAddress()).thenReturn(inetAddressMocked);
		when(socket.getLocalPort()).thenReturn(Utils.generateRandomInteger());
		when(inetAddressMocked.getHostName()).thenReturn("MockHostName");
	}
}
