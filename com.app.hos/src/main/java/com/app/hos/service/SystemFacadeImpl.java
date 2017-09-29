package com.app.hos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.service.integration.server.Server;
import com.app.hos.service.managers.command.CommandManager;
import com.app.hos.service.managers.connection.ConnectionManager;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.result.NewDevice;
import com.app.hos.share.command.type.CommandType;

@Service
public class SystemFacadeImpl implements SystemFacade {

	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private DeviceManager deviceManager;
	
	@Autowired
	private ConnectionManager connectionManager;
	
	@Autowired
	private CommandManager commandManager;
	
	@Autowired
	private Server server;
	
	public void receivedCommand(MessageHeaders headers, Command command) {
		CommandType type = CommandType.valueOf(command.getCommandType());
		if (type.isExecutable()) {
			commandManager.executeCommand(headers, command);
		} else {
			if (CommandType.BAD_CONVERSION == type) {
				
			} else if (CommandType.HELLO == type) {
				NewDevice device = (NewDevice)command.getResult();
				createNewDeviceAndSendRepsonse(headers,device,command.getSerialId());
			} else if (CommandType.MY_STATUS == type) {
				DeviceStatus status = (DeviceStatus)command.getResult();
				deviceManager.addDeviceStatus(command.getSerialId(), status);
			} 
		}
	}

	public void closeConnection(String connectionId) {
		getConnectionFactory().closeConnection(connectionId);
		connectionManager.generateHistoryConnection(connectionId);
	}
	
	public void sendCommand(String connectionId, CommandType type) {
		Message<Command> message = commandManager.createMessage(connectionId,type);
		server.sendMessage(message);
	}

	public boolean isConnectionOpen (String connectionId) {
		List<String> conectionIds = getConnectionFactory().getOpenConnectionIds();
		return conectionIds.contains(connectionId);
	}
	
	
	private void createNewDeviceAndSendRepsonse(MessageHeaders headers,NewDevice newDevice, String serialId) {
		deviceManager.openDeviceConnection(headers, newDevice.getName(), serialId);
		// send command as a response
		String connectionId = (String)headers.get(IpHeaders.CONNECTION_ID);
		sendCommand(connectionId,CommandType.HELLO);
	}	
	
	private AbstractConnectionFactory getConnectionFactory() {
		return (AbstractConnectionFactory)appContext.getBean("hosServer");
	}

	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}

	public void setServer(Server server) {
		this.server = server;
	}
	

}
