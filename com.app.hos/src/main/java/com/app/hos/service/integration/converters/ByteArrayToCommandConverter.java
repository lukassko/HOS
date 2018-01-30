package com.app.hos.service.integration.converters;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandBuilder;
import com.app.hos.share.command.builder.concretebuilders.BadConversionCommandBuilder;

public class ByteArrayToCommandConverter implements Serializer<Command>, Deserializer<Command>{
	
	private CommandBuilder commandBuilder = new CommandBuilder();
	
	public Command deserialize(InputStream inputStream) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(inputStream);
		Command cmd = null;
		try {
			cmd = (Command) ois.readObject();
		} catch (Exception e) {
			commandBuilder.setCommandBuilder(new BadConversionCommandBuilder());
			commandBuilder.createCommand();
			cmd = commandBuilder.getCommand();
		} 
		return cmd;
	}

	public void serialize(Command arg, OutputStream outputStream) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(outputStream);
		oos.writeObject(arg);
		oos.flush();
	}
}
