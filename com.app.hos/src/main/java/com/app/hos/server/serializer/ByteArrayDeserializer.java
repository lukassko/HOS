package com.app.hos.server.serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.serializer.Deserializer;

public class ByteArrayDeserializer implements Deserializer<byte[]> {

	@Override
	public byte[] deserialize(InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int readByte;
	    while ((readByte = inputStream.read()) != -1) {
	        buffer.write(readByte);
	    }
		return buffer.toByteArray();
	}

}
