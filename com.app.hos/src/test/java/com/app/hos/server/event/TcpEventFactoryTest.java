package com.app.hos.server.event;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;
import com.app.hos.server.connection.SocketInfo;
import com.app.hos.server.factory.TcpServerListener;

public class TcpEventFactoryTest {

	@Test
	public void getConnectionExcpetionFromEventFactoryShouldReturnTcpConnectionExceptionEventInstance() throws Exception {
		// given
		SocketInfo socketInfo = mock(SocketInfo.class);
		
		// when
		TcpEvent event = TcpEventFactory.CONNECTION_EXCEPTION.create(socketInfo, new IOException());
		
		// then
		assertThat(event, instanceOf(TcpConnectionExceptionEvent.class));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getConnectionExcpetionFromEventFactoryWithInvalidArgumentShouldThrowInvalidArgumentException() throws Exception {
		// given
		TcpServerListener invalidArg = mock(TcpServerListener.class);
		
		// when
		TcpEventFactory.CONNECTION_EXCEPTION.create(invalidArg, new IOException());
		
		// then
			// exception
	}
	
	@Test
	public void getOpenConnectionFromEventFactoryShouldReturnTcpOpenConnectionEventInstance() throws Exception {
		// given
		SocketInfo socketInfo = mock(SocketInfo.class);
		
		// when
		TcpEvent event = TcpEventFactory.OPEN_CONNECTION.create(socketInfo);
		
		// then
		assertThat(event, instanceOf(TcpOpenConnectionEvent.class));
	}
	
	@Test
	public void getCloseConnectionFromEventFactoryShouldReturnTcpCloseConnectionEventInstance() throws Exception {
		// given
		SocketInfo socketInfo = mock(SocketInfo.class);
		
		// when
		TcpEvent event = TcpEventFactory.CLOSE_CONNECTION.create(socketInfo);
		
		// then
		assertThat(event, instanceOf(TcpCloseConnectionEvent.class));
	}
	
	@Test
	public void getServerExcpetionFromEventFactoryShouldReturnTcpServerExceptionEventInstance() throws Exception {
		// given
		TcpServerListener server = mock(TcpServerListener.class);
		
		// when
		TcpEvent event = TcpEventFactory.SERVER_EXCEPTION.create(server);
		
		// then
		assertThat(event, instanceOf(TcpServerExceptionEvent.class));
	}
	
	@Test
	public void getCorrelationConnectionExceptionFromEventFactoryShouldReturnTcpConnectionCorrelationFailedEventInstance() throws Exception {
		// given
		
		// when
		TcpEvent event = TcpEventFactory.CORRELATION_CONNECTION_EXCEPTION.create("test_string");
		
		// then
		assertThat(event, instanceOf(TcpConnectionCorrelationFailedEvent.class));
	}
}
