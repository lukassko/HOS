package com.app.hos.service.integration.converters;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.share.command.Command;

public class ByteArrayToStringConverter implements Serializer<String>, Deserializer<String>{

	
	public String deserialize(InputStream inputStream) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(inputStream);
		Command cmd = null;
		String name = null;
		try {
			cmd = (Command) ois.readObject();
			name = cmd.getClientName();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return name;
	}

	public void serialize(String arg, OutputStream outputStream) throws IOException {
		ObjectOutputStream objStream = new ObjectOutputStream(outputStream);
		objStream.writeObject(new String(arg));
		objStream.flush();
	}

}
