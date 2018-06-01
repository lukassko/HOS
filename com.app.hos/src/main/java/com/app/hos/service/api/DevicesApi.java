package com.app.hos.service.api;

import java.util.List;
import java.util.Map;

import com.app.hos.persistance.custom.DateTime;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;

public interface DevicesApi {

	public boolean removeDevice(String serial);
	
	public Map<Device, DeviceStatus> getConnectedDevices();
	
	public List<DeviceStatus> getDeviceStatuses(String serial, DateTime from, DateTime to);
}
