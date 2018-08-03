package com.app.hos.service.command;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.service.command.builder_v2.Command;
import com.app.hos.service.command.type.CommandType;
import com.app.hos.service.exceptions.SerializationException;
import com.app.hos.utils.SerializationUtils;

public class CommandTest {

	private SerializationUtils<Command> serializer = new SerializationUtils<Command>();
	
	private String fileName = "command.test";
	
	@Test
	public void serializeCommandToFileAndDeserializeShouldGetSameObject () throws SerializationException, FileNotFoundException {
		// given
		Command command = new Command();
		command.setCommandType(CommandType.GET_STATUS);
		command.setStatus(true);
		
		// when
		serializer.serialize(command, new FileOutputStream(fileName));
		Command deserialized = serializer.deserialize(new FileInputStream(fileName));

		// then
		Assert.assertEquals(command.getCommandType(), deserialized.getCommandType());
		Assert.assertEquals(command.getStatus(), deserialized.getStatus());
		Assert.assertEquals(command.getResult(), deserialized.getResult());
	}

}
