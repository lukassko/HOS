package com.app.hos.service.integration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

public class ByteArrayToStringConverter implements Serializer<String>, Deserializer<String>{

	public String deserialize(InputStream inputStream) throws IOException {
		String value = parseString(inputStream);
		System.out.println("PARSED vaulue: " + value);
		return value;
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
