package com.app.hos.service.managers.device;

import java.util.Set;

import com.app.hos.persistance.models.Device;

public interface DeviceInformation {
	
	public Set<Device> getConnectedDevices();
	
	public Device getDeviceStatus();
}
