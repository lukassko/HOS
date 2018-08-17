package com.app.hos.server.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.springframework.core.serializer.Deserializer;

// TODO make class generic
// use optional
public class ObjectDeserializer implements Deserializer<Object> {

	@Override
	public Object deserialize(InputStream inputStream) throws IOException {
	    ObjectInputStream is = new ObjectInputStream(inputStream);
		Object opt;
		try {
			opt = is.readObject();
		} catch (ClassNotFoundException e) {
			opt = null;
		}
		return opt;
	}

}
