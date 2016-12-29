package com.app.hos.service.integration.converters;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.share.command.Command;

public class ByteArrayToStringConverter implements Serializer<String>, Deserializer<String>{

	public String deserialize(InputStream inputStream) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(inputStream);
		Command cmd = null;
		String name = "";
		try {
			cmd = (Command) ois.readObject();
			System.out.println("PARSED vaulue: " + cmd.getClientName() + " | " + cmd.getCommandType());
			name = cmd.getClientName();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name;
	}

	public void serialize(String arg, OutputStream outputStream) throws IOException {
		System.out.println("SERIALIZE: " + arg);
		ObjectOutputStream objStream = new ObjectOutputStream(outputStream);
		objStream.writeObject(new String(arg));
		objStream.flush();
	}

}
