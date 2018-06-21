package com.app.hos.service.integration.converters;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import com.app.hos.service.exceptions.SerializationException;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.builder_v2.CommandBuilder;
import com.app.hos.share.command.builder_v2.concretebuilders.BadConversionCommandBuilder;
import com.app.hos.utils.SerializationUtils;

public class ByteArrayToCommandConverter implements Serializer<Command>, Deserializer<Command>{
	
	private CommandBuilder commandBuilder = new CommandBuilder();
	
	private SerializationUtils<Command> serializer = new SerializationUtils<Command>();
	
	public Command deserialize(InputStream inputStream) throws IOException {
		Command cmd = null;
		try {
			cmd = serializer.deserialize(inputStream);
		} catch (SerializationException e) {
			commandBuilder.setCommandBuilder(new BadConversionCommandBuilder(e.toString()));
			cmd =commandBuilder.createCommand();
		} 
		return cmd;
	}

	public void serialize(Command cmd, OutputStream outputStream) throws IOException {
		serializer.serialize(cmd, outputStream);
	}
}
