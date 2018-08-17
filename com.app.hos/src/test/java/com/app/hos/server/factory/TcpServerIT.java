package com.app.hos.server.factory;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import static org.mockito.Mockito.*;

import com.app.hos.server.TcpReceivingMessageAdapter;
import com.app.hos.server.TcpSendingMessageAdapter;
import com.app.hos.server.connection.Connection;
import com.app.hos.server.event.TcpEvent;
import com.app.hos.server.factory.ConnectionManager;
import com.app.hos.server.factory.SocketFactory;
import com.app.hos.server.factory.TcpServer;
import com.app.hos.server.handler.MessageHandler;
import com.app.hos.server.handler.TcpThreadsExecutor;
import com.app.hos.server.messaging.IpHeaders;
import com.app.hos.server.messaging.MessageBuilder;
import com.app.hos.server.messaging.TcpMessageMapper;
import com.app.hos.server.serializer.ObjectDeserializer;
import com.app.hos.server.serializer.StandardSerializer;
import com.app.hos.service.command.builder_v2.Command;
import com.app.hos.service.command.builder_v2.CommandBuilder;
import com.app.hos.service.command.builder_v2.concretebuilders.HelloCommandBuilder;
import com.app.hos.service.command.type.CommandType;

public class TcpServerIT {

	private final Logger logger = Logger.getLogger(getClass().getName());
	
	@Rule
    public MockitoRule rule = MockitoJUnit.rule();
	
	private final int PORT = 12040;
	
	private TcpServer tcpServer;
	
	private ConnectionManager connectionManager = new TcpConnectionManager();
	
	private SocketFactory socketFactory = new TcpSocketFactory();
	
	private TcpThreadsExecutor threadsExecutor = new TcpThreadsExecutor();
	
	private TcpMessageMapper messageMapper = new TcpMessageMapper();
	
	private TcpSendingMessageAdapter sendingMessageAdapter;
	
	private TcpReceivingMessageAdapter receivingMessageAdapter;
	
	private ObjectDeserializer deserializer = new ObjectDeserializer();
	
	private StandardSerializer serializer = new StandardSerializer();
	
	@Mock                  
	private MessageHandler messageHandler;
	
	@Mock
	private ApplicationEventPublisher applicationEventPublisher;

	@Before
	public void prepareServer() throws IOException {
		sendingMessageAdapter = new TcpSendingMessageAdapter(tcpServer);
		sendingMessageAdapter.setConnectionManager(connectionManager);
		
		receivingMessageAdapter = new TcpReceivingMessageAdapter(tcpServer);
		receivingMessageAdapter.setMessageHandler(messageHandler);
		
		this.tcpServer = new TcpServerBuilder(PORT)
									.withApplicationEventPublisher(applicationEventPublisher)
									.withConnnectionManager(connectionManager)
									.withSocketFactory(socketFactory)
									.withListener(receivingMessageAdapter)
									.withThreadsExecutor(threadsExecutor)
									.withMessageMapper(messageMapper)
									.withSerializer(serializer)
									.withDeserializer(deserializer)
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
			final CountDownLatch finished = new CountDownLatch(4); // expected 4 events
			final CountDownLatch afterOpenConnection = new CountDownLatch(1); // wait for first event

			doAnswer(
				invocation -> {
					Object[] args = invocation.getArguments();
					TcpEvent event = (TcpEvent) args[0];
					logger.info(event.toString());
					afterOpenConnection.countDown();
					finished.countDown();
					return null;
				}
			).when(applicationEventPublisher).publishEvent(isA(TcpEvent.class));
			
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
			verify(applicationEventPublisher, times(4)).publishEvent(isA(TcpEvent.class));
					
			// verify connection is closed
			assertFalse(connection.isOpen());
	
			// verify that no opened connections are available after server stop
			connections = new ArrayList<>(connectionManager.getConnections());
			assertTrue(connections.isEmpty());
			assertNull(connectionManager.getConnection(connectionId));	
		} finally {
			clientSocket.close();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void exchangeMessagesBetweenClientAndServerSocket() throws Exception {
		Socket clientSocket = null;
		try {
			ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
			final CountDownLatch afterOpenConnection = new CountDownLatch(1); 
			doAnswer(
				invocation -> {
					Object[] args = invocation.getArguments();
					TcpEvent event = (TcpEvent) args[0];
					logger.info(event.toString());
					afterOpenConnection.countDown();
					return null;
				}
			).when(applicationEventPublisher).publishEvent(isA(TcpEvent.class));
			
			// create socket connecting to server
			clientSocket = new Socket("localhost", PORT); 
			
			// wait to publish new connection
			afterOpenConnection.await(1, TimeUnit.SECONDS);
			
			// get active connection
			List<Connection> connections = new ArrayList<>(connectionManager.getConnections());
			assertFalse(connections.isEmpty());
			Connection connection = connections.get(0);
			String connectionId = connection.getConnectionId();
			
			// send message by connection (server side)
			sendingMessageAdapter.handleMessage(getMessage(connectionId));
			
			// verify client socket received connection
			Object payload = getCommandFromSocket(clientSocket);
			assertNotNull(payload);
			Command command = (Command)payload;
			assertEquals(CommandType.HELLO, command.getCommandType());
			
			// send message by socket (client side)
			serializer.serialize(getPayload(), clientSocket.getOutputStream());
			
			// verify tcpListener was called
			verify(messageHandler, timeout(1000).times(1)).processMessage(messageCaptor.capture());
			
			// verify received message
			Message<?> receivedMessage = messageCaptor.getValue();
			Command receivedCommand = (Command)receivedMessage.getPayload();
			MessageHeaders headers = receivedMessage.getHeaders();
			String receivedConnectionId = (String)headers.get(IpHeaders.CONNECTION_ID);
			assertEquals(connectionId, receivedConnectionId);
			assertEquals(CommandType.HELLO, receivedCommand.getCommandType());
			
			// close connection
			connectionManager.closeConnection(connectionId);
			
			// verify connection is not available on server
			assertFalse(connection.isOpen());
			assertNull(connectionManager.getConnection(connectionId));

		} finally {
			clientSocket.close();
		}
	}
	
	private Object getCommandFromSocket(Socket socket) throws IOException {
		InputStream inputStream = socket.getInputStream();
		return deserializer.deserialize(inputStream);
	}
	

	private Message<?> getMessage(String connectionId) {
		return MessageBuilder.withPayload(getPayload())
								.addHeader(IpHeaders.CONNECTION_ID, connectionId)
								.build();
	}
	
	private Command getPayload() {
		CommandBuilder builder = new CommandBuilder();
		builder.setCommandBuilder(new HelloCommandBuilder());
		return builder.createCommand();
	}
}
