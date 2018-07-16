package com.app.hos.server.serializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.springframework.core.serializer.Serializer;

public class StandardSerializer implements Serializer<Object> {

	@Override
	public void serialize(Object object , OutputStream outputStream) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(outputStream);
		oos.writeObject(object);
		oos.flush();
	}

}
