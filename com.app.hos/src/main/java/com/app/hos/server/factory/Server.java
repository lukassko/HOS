package com.app.hos.server.factory;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.server.TcpListener;

public interface Server {

	public void start();
	
	public void stop();
	
	public void setMapper(Object mapper);
	
	public void setSerializer(Serializer<?> serializer);
	
	public void setDeserializer(Deserializer<?> deserializer);
	
	public void setListener(TcpListener listener);
}
