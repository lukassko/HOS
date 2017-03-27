package com.app.hos.service.managers.device;

import org.springframework.messaging.MessageHeaders;

public interface DeviceCreator {

	public boolean addDevice(MessageHeaders messageHeaders);
	
	public boolean removeDevice(String connectionId);
}
