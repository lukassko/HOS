package com.app.hos.server.factory;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.server.TcpListener;
import com.app.hos.server.messaging.TcpMessageMapper;

public interface Server {

	public void start();
	
	public void stop();
	
	public void setMapper(TcpMessageMapper mapper);
	
	public void setSerializer(Serializer<?> serializer);
	
	public void setDeserializer(Deserializer<?> deserializer);
	
	public void setListener(TcpListener listener);
	
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher);
	
}
