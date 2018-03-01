package com.app.hos.service.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.service.exceptions.handler.ExceptionUtils;
import com.app.hos.service.integration.server.Server;
import com.app.hos.service.managers.ConnectionManager;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.service.managers.command.CommandManager;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.builder.CommandFactory;
import com.app.hos.share.command.result.NewDevice;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.Utils;

@Service
public class SystemFacadeImpl implements SystemFacade  {

	@Autowired
	private DeviceManager deviceManager;
	
	@Autowired
	private ConnectionManager connectionManager;
	
	@Autowired
	private CommandManager commandManager;
	
	@Autowired
	private Server server;

	
	private ExecutorService commandExecutor = Executors.newFixedThreadPool(4);
	
	// setters - used to test for Mocks (try to remove)
	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}
	
	// commands API
	@Override
	public void receivedCommand(final MessageHeaders headers, final Command command) {
		final CommandType type = CommandType.valueOf(command.getCommandType());
		if (type.isExecutable()) {
			String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
			try {
				commandManager.executeCommand(connectionId, command);
			} catch (NotExecutableCommandException e) {
				ExceptionUtils.handle(e);
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

	@Override
	public void sendCommand(String connectionId, CommandType type) {
		Command command = CommandFactory.getCommand(type);
		server.sendMessage(createMessage(connectionId, command));
	}
	
	@Override
	public void sendCommand(String connectionId, Command command) {
		server.sendMessage(createMessage(connectionId, command));
	}
	
	// connections API
	@Override
	public boolean closeConnection(String connectionId) {
		boolean isConnectionClose = getConnectionFactory().closeConnection(connectionId);
		if (isConnectionClose) 
			connectionManager.finalizeConnection(connectionId);
		return isConnectionClose;
	}

	@Override
	public boolean isConnectionOpen (String connectionId) {
		List<String> conectionIds = getConnectionFactory().getOpenConnectionIds();
		return conectionIds.contains(connectionId);
	}
	
	// devices API
	@Override
	@Transactional
	public boolean removeDevice(String serial) {
		Device device = deviceManager.findDeviceBySerial(serial);
		Connection connection = device.getConnection();
		if (closeConnection(connection.getConnectionId())) {
			deviceManager.removeDevice(device);
			return true;
		} 
		return false;
	}

	@Override
	public Map<Device, DeviceStatus> getConnectedDevices() {
		return deviceManager.getConnectedDevices();
	}
	
	@Override
	public List<DeviceStatus> getDeviceStatuses(String serial, DateTime from, DateTime to) {
		return deviceManager.getDeviceStatuses(serial, from, to);
	}
	
	// private methods
	private Message<Command> createMessage(String connectionId, Command command) {
		return MessageBuilder.withPayload(command).setHeader(IpHeaders.CONNECTION_ID, connectionId).build();
	}
		
	private AbstractConnectionFactory getConnectionFactory() {
		return (AbstractConnectionFactory)Utils.getObjectFromContext("hosServer");
	}

}
