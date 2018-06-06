package com.app.hos.service.api;

import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.connection.AbstractConnectionFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.connection.Connection;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.service.AbstractMapFactory;
import com.app.hos.service.exceptions.NotExecutableCommandException;
import com.app.hos.service.integration.server.Server;
import com.app.hos.service.managers.ConnectionManager;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.service.managers.command.CommandManager;
import com.app.hos.share.command.CommandInfo;
import com.app.hos.share.command.builder_v2.AbstractCommandBuilder;
import com.app.hos.share.command.builder_v2.Command;
import com.app.hos.share.command.type.CommandType;
import com.app.hos.utils.ReflectionUtils;

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

	@Autowired
	private AbstractMapFactory<CommandType,
									Class<? extends AbstractCommandBuilder>, Command> commandFactory;

	// TODO setters - used to test for Mocks (try to remove)
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
		String connectionId = headers.get(IpHeaders.CONNECTION_ID).toString();
		CommandInfo commandInfo = new CommandInfo(connectionId, command);
		try {
			commandManager.executeCommand(commandInfo);
		} catch (NotExecutableCommandException e) {
			sendCommand(connectionId,CommandType.UNKNOWN);
			//ExceptionUtils.handle(e);
		}
	}

	@Override
	public void sendCommand(CommandInfo command) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void sendCommand(String connectionId, CommandType type) {
		Command command = commandFactory.get(type);
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
		return (AbstractConnectionFactory)ReflectionUtils.getObjectFromContext("hosServer");
	}

}
