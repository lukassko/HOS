package com.app.hos.service.integration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

public class ByteArrayToStringConverter implements Serializer<String>, Deserializer<String>{

	private final int SIZE = 10;
	public String deserialize(InputStream inputStream) throws IOException {
		System.out.println("DESERIALIZE");
		
		String value = parseString(inputStream);
		inputStream.close();
		return value;
	}

	public void serialize(String arg, OutputStream outputStream) throws IOException {
		System.out.println("SERIALIZE: " + arg);
		byte[] number = arg.getBytes();
		
		outputStream.write(number);
		outputStream.flush();
	}

	private String parseString (InputStream stream) throws IOException {
		StringBuilder builder = new StringBuilder();
		int c;
		for (int i = 0; i<SIZE; i++) {
			c = stream.read();
			if (c == -1) 
				break;
			builder.append((char)c);
		}
		return builder.toString();
		
	}
}
