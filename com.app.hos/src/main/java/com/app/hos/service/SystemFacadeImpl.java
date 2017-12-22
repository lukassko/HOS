package com.app.hos.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.service.integration.server.Server;
import com.app.hos.service.managers.command.CommandManager;
import com.app.hos.service.managers.connection.ConnectionManager;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandFactory;
import com.app.hos.share.command.result.NewDevice;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.utils.exceptions.NotExecutableCommand;

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

	private ExecutorService commandExecutor = Executors.newFixedThreadPool(4);
	
	// setters
	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}
	
	
	public void receivedCommand(final MessageHeaders headers, final Command command) {
		final CommandType type = CommandType.valueOf(command.getCommandType());
		if (type.isExecutable()) {
			String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
			try {
				commandManager.executeCommand(connectionId, command);
			} catch (NotExecutableCommand e) {
				e.printStackTrace();
			}
		} else {
			final String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
			Runnable commandThread = null;
			if (CommandType.BAD_COMMAND_CONVERSION == type) { // send information about bad command serialization
				commandThread = new Runnable() {
					public void run() {
						sendCommand(connectionId,type);
					}
				};
			} else if (CommandType.HELLO == type) { // create new device and send 'Hello' response
				commandThread = new Runnable() {
					public void run() {
						NewDevice device = (NewDevice)command.getResult();
						deviceManager.openDeviceConnection(headers, device.getName(), command.getSerialId());
						sendCommand(connectionId,type);
					}
				};
				
			} else if (CommandType.MY_STATUS == type) { // add new status to database for device
				commandThread = new Runnable() {
					public void run() {
						DeviceStatus status = (DeviceStatus)command.getResult();
						deviceManager.addDeviceStatus(command.getSerialId(), status);
					}
				};
			} else { // send unknown command information
				commandThread = new Runnable() {
					public void run() {
						sendCommand(connectionId,CommandType.UNKNOWN_COMMAND);
					}
				};
			}
			commandExecutor.execute(commandThread);
		}
	}

	// public API
	public boolean closeConnection(String connectionId) {
		boolean isConnectionClose = getConnectionFactory().closeConnection(connectionId);
		if (isConnectionClose) 
			connectionManager.finalizeConnection(connectionId);
		return isConnectionClose;
	}
	
	public void sendCommand(String connectionId, CommandType type) {
		Command command = CommandFactory.getCommand(type);
		server.sendMessage(createMessage(connectionId, command));
	}

	public void sendCommand(String connectionId, Command command) {
		server.sendMessage(createMessage(connectionId, command));
	}
	
	public boolean isConnectionOpen (String connectionId) {
		List<String> conectionIds = getConnectionFactory().getOpenConnectionIds();
		return conectionIds.contains(connectionId);
	}
	
	@Override
	@Transactional
	public void removeDevice(String serial) {
		Device device = deviceManager.findDeviceBySerial(serial);
		Connection connection = device.getConnection();
		if (closeConnection(connection.getConnectionId())) {
			deviceManager.removeDevice(device);
		}
	}
	
	// private methods
	private Message<Command> createMessage(String connectionId, Command command) {
		return MessageBuilder.withPayload(command).setHeader(IpHeaders.CONNECTION_ID, connectionId).build();
	}
		
	private AbstractConnectionFactory getConnectionFactory() {
		return (AbstractConnectionFactory)appContext.getBean("hosServer");
	}

}
