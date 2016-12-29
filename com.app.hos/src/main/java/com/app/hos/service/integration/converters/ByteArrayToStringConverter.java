package com.app.hos.service.integration.converters;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

public class ByteArrayToStringConverter implements Serializer<String>, Deserializer<String>{

	public String deserialize(InputStream inputStream) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(inputStream);
		Object object = "TEST";
		try {
			object = ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String value = parseString(inputStream);
		System.out.println("PARSED vaulue: " + object.toString());
		return object.toString();
	}

	public void serialize(String arg, OutputStream outputStream) throws IOException {
		System.out.println("SERIALIZE: " + arg);
		byte[] bytes = arg.getBytes();
		outputStream.write(bytes);
		outputStream.flush();
	}

	private String parseString (InputStream stream) throws IOException {
		StringBuilder builder = new StringBuilder();
		int c = stream.read();
		while (c != 10) {
			builder.append((char)c);
			c = stream.read();
		}
		return builder.toString();
	}
}
