package com.app.hos.tests.units.command;


import org.junit.Assert;
import org.junit.Test;

import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandFactory;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.utils.converters.CommandConverter;
import com.app.hos.utils.exceptions.NotExecutableCommandException;

public class CommandConverterTest {

	@Test
	public void convertCommandFromNonExecutableToExecutable() throws NotExecutableCommandException {
		Command nonExecutabaleCommand = CommandFactory.getCommand(CommandType.GET_STATUS);
		CommandConverter.getExecutableCommand(nonExecutabaleCommand);
		//Assert.assertEquals(CommandType.HELLO, command.getEnumeratedCommandType());
	}
	
}
