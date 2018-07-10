package com.app.hos.server.serializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Optional;

import org.springframework.core.serializer.Deserializer;

public class ObjectDeserializer implements Deserializer<Optional<Object>> {

	@Override
	public Optional<Object> deserialize(InputStream inputStream) throws IOException {
		ByteArrayDeserializer deserializer = new ByteArrayDeserializer();
		byte [] bytes = deserializer.deserialize(inputStream); 
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
	    ObjectInputStream is = new ObjectInputStream(in);
		Optional<Object> opt;
		try {
			opt = Optional.of(is.readObject());
		} catch (ClassNotFoundException e) {
			opt = Optional.empty();
		}
		return opt;
	}

}
