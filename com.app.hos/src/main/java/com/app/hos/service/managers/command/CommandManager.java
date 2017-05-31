package com.app.hos.service.managers.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import com.app.hos.service.integration.server.Server;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.command.CommandBuilder;
import com.app.hos.share.command.CommandFactory;
import com.app.hos.share.command.HelloCommandBuilder;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandType;
import com.app.hos.share.command.result.DeviceStatus;
import com.app.hos.share.command.result.NewDevice;

@Service
public class CommandManager implements CommandExecutor {

	//private TaskStrategy taskStrategy; inject
	
	private DeviceManager deviceManager;

	private Server server;
		
	@Autowired
	public CommandManager(DeviceManager DeviceManager,Server server) {
		this.deviceManager = DeviceManager;
		this.server = server;
	}

	public void sendCommand(String connectionId, CommandType type) {
		if(connectionId != null) {
			Message<Command> message = createMessage(connectionId,type);
			server.sendMessage(message);
		}	
	}
	
	public void executeCommand(MessageHeaders headers, Command command) {
		CommandType type = CommandType.valueOf(command.getCommandType());
		if (type.isExecutable()) {
			//executeTask(command);
		} else {
			if (CommandType.BAD_CONVERSION == type) {
				
			} else if (CommandType.HELLO == type) {
				NewDevice device = (NewDevice)command.getResult();
				getCommandResult(headers,device,command.getSerialId());
			} else if (CommandType.MY_STATUS == type) {
				DeviceStatus status = (DeviceStatus)command.getResult();
				getCommandResult(command.getSerialId(), status);
			} 
		}
	}

	private void getCommandResult(String serialId, DeviceStatus deviceStatus) {
		deviceManager.addDeviceStatus(serialId, deviceStatus);
	}
	
	private void getCommandResult(MessageHeaders headers,NewDevice newDevice, String serialId) {
		deviceManager.createDevice(headers, newDevice.getName(), serialId);
		// send command as a response
		String connectionId = (String)headers.get(IpHeaders.CONNECTION_ID);
		sendCommand(connectionId,CommandType.HELLO);
	}	

	private Message<Command> createMessage(String connectionId, CommandType type) {
		Command command = CommandFactory.getCommand(type);
		Message<Command> message = MessageBuilder.withPayload(command)
		        .setHeader(IpHeaders.CONNECTION_ID, connectionId)
		        .build();		
		return message;
	}
}
