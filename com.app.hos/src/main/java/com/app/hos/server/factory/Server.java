package com.app.hos.server.factory;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.server.handler.TcpListener;
import com.app.hos.server.handler.ThreadsExecutor;
import com.app.hos.server.messaging.TcpMessageMapper;

public interface Server {

	public void start();
	
	public void stop();

	public boolean isListening();
	
	public void setMapper(TcpMessageMapper mapper);
	
	public void setSerializer(Serializer<?> serializer);
	
	public void setDeserializer(Deserializer<?> deserializer);
	
	public void setListener(TcpListener listener);
	
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher);
	
	public void setConnnectionManager(ConnectionManager connectionFactory);
	
	public void setSocketFactory(SocketFactory socketFactory);
	
	public void setThreadsExecutor(ThreadsExecutor threadsExecutor);
}
