package com.app.hos.service;

import java.util.List;
import java.util.Map;

import org.springframework.messaging.MessageHeaders;

import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.models.DeviceStatus;
import com.app.hos.pojo.WebDeviceStatusesRequest;
import com.app.hos.share.command.builder.Command;
import com.app.hos.share.command.type.CommandType;

public interface SystemFacade {
	
	// commands API
	public void receivedCommand(MessageHeaders headers, Command command);
	
	public void sendCommand(String connectionId, CommandType type);
	
	public void sendCommand(String connectionId, Command command);
		
	// connections API
	public boolean closeConnection(String connectionId);
	
	public boolean isConnectionOpen (String connectionId);
	
	// devices API
	public boolean removeDevice(String serial);
	
	public Map<Device, DeviceStatus> getConnectedDevices();
	
	public List<DeviceStatus> getDeviceStatuses(WebDeviceStatusesRequest request);
}
