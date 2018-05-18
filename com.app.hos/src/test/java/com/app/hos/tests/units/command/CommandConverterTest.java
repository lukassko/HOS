package com.app.hos.tests.units.command;


import org.junit.Assert;
import org.junit.Test;

import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.share.command.FutureCommandFactory;
import com.app.hos.share.command.builder.CommandFactory;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.type.CommandType;

public class CommandConverterTest {

	@Test
	public void convertCommandFromNonExecutableToExecutable() throws NotExecutableCommandException {
		Command nonExecutabaleCommand = CallableCommandFactory.getCommand(CommandType.GET_STATUS);
		FutureCommandFactory.getCommand(nonExecutabaleCommand);
		//Assert.assertEquals(CommandType.HELLO, command.getEnumeratedCommandType());
	}
	
}
