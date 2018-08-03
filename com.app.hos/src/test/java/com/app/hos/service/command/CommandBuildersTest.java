package com.app.hos.service.command;

import org.junit.Assert;
import org.junit.Test;

import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.service.command.builder_v2.Command;
import com.app.hos.service.command.builder_v2.CommandBuilder;
import com.app.hos.service.command.builder_v2.concretebuilders.BadConversionCommandBuilder;
import com.app.hos.service.command.builder_v2.concretebuilders.GetStatusCommandBuilder;
import com.app.hos.service.command.builder_v2.concretebuilders.HelloCommandBuilder;
import com.app.hos.service.command.builder_v2.concretebuilders.MyStatusCommandBuilder;
import com.app.hos.service.command.result.Message;
import com.app.hos.service.command.result.NewDevice;
import com.app.hos.service.command.type.CommandType;
import com.app.hos.service.command.type.DeviceType;
import com.app.hos.utils.Utils;

public class CommandBuildersTest {

	CommandBuilder commandBuilder = new CommandBuilder();
	
	@Test
	public void testBadConversionCommandBuilder() {
		// given
		String msg = "Some massage";
		BadConversionCommandBuilder builder = new BadConversionCommandBuilder(msg);
		
		// when
		Command command = builder.createCommand()
										.setCommandType()
										.setResult()
										.setStatus().getCommand();
		
		// then
		Assert.assertEquals(CommandType.BAD_CONVERSION, command.getCommandType());
		Assert.assertFalse(command.getStatus());
		
		Message message = (Message)command.getResult();
		Assert.assertEquals(message.getMessage(), msg);
	}
	

	@Test
	public void testCommandBuilderWithBadConversionCommandBuilder () {
		// given
		String msg = "Some massage";
		BadConversionCommandBuilder builder = new BadConversionCommandBuilder(msg);
		
		// when 
		commandBuilder.setCommandBuilder(builder);
		Command command = commandBuilder.createCommand();
		
		// then
		Assert.assertEquals(CommandType.BAD_CONVERSION, command.getCommandType());
		Assert.assertFalse(command.getStatus());
			
		Message message = (Message)command.getResult();
		Assert.assertEquals(message.getMessage(), msg);
	}
	
	@Test
	public void testGetStatusCommandBuilder() {
		// given
		GetStatusCommandBuilder builder = new GetStatusCommandBuilder();
		
		// when 
		Command command = builder.createCommand()
										.setCommandType()
										.setResult()
										.setStatus().getCommand();
		
		// then
		Assert.assertEquals(CommandType.GET_STATUS, command.getCommandType());
		Assert.assertTrue(command.getStatus());
		Assert.assertNull(command.getResult());
	}
	
	@Test
	public void testCommandBuilderWithGetStatusCommandBuilder () {
		// given
		GetStatusCommandBuilder builder = new GetStatusCommandBuilder();
		
		// when 
		commandBuilder.setCommandBuilder(builder);
		Command command = commandBuilder.createCommand();
		
		// then
		Assert.assertEquals(CommandType.GET_STATUS, command.getCommandType());
		Assert.assertTrue(command.getStatus());
		Assert.assertNull(command.getResult());
	}
	
	@Test
	public void testMyStatusCommandBuilder() {
		// given
		MyStatusCommandBuilder builder = new MyStatusCommandBuilder();
		
		// when 
		Command command = builder.createCommand()
										.setCommandType()
										.setResult()
										.setStatus().getCommand();
		
		// then
		Assert.assertEquals(CommandType.MY_STATUS, command.getCommandType());
		Assert.assertTrue(command.getStatus());
		
		DeviceStatus status =  (DeviceStatus)command.getResult();
		Assert.assertTrue(status.getCpuUsage() != 0);
		Assert.assertTrue(status.getRamUsage() != 0);
	}
	
	@Test
	public void testCommandBuilderWithMyStatusCommandBuilder () {
		// given
		MyStatusCommandBuilder builder = new MyStatusCommandBuilder();
		
		// when 
		commandBuilder.setCommandBuilder(builder);
		Command command = commandBuilder.createCommand();
		
		// then
		Assert.assertEquals(CommandType.MY_STATUS, command.getCommandType());
		Assert.assertTrue(command.getStatus());
		
		DeviceStatus status =  (DeviceStatus)command.getResult();
		Assert.assertTrue(status.getCpuUsage() != 0);
		Assert.assertTrue(status.getRamUsage() != 0);
	}
	
	@Test
	public void testHelloCommandBuilder() {
		// given
		HelloCommandBuilder builder = new HelloCommandBuilder();
		
		// when 
		Command command = builder.createCommand()
										.setCommandType()
										.setResult()
										.setStatus().getCommand();
		
		// then
		Assert.assertEquals(CommandType.HELLO, command.getCommandType());
		Assert.assertTrue(command.getStatus());
		
		NewDevice device =  (NewDevice)command.getResult();
		Assert.assertEquals(DeviceType.SERVER, device.getType());
		Assert.assertEquals(Utils.getHostName(), device.getName());
	}
	
	@Test
	public void testCommandBuilderWithHelloCommandBuilder () {
		// given
		HelloCommandBuilder builder = new HelloCommandBuilder();
		
		// when 
		commandBuilder.setCommandBuilder(builder);
		Command command = commandBuilder.createCommand();
		
		// then
		Assert.assertEquals(CommandType.HELLO, command.getCommandType());
		Assert.assertTrue(command.getStatus());
		
		NewDevice device =  (NewDevice)command.getResult();
		Assert.assertEquals(DeviceType.SERVER, device.getType());
		Assert.assertEquals(Utils.getHostName(), device.getName());
	}
}
