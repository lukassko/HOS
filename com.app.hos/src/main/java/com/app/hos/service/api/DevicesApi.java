package com.app.hos.service.api;

import java.util.List;
import java.util.Map;

import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceStatus;
import com.app.hos.share.utils.DateTime;

public interface DevicesApi {

	public boolean removeDevice(String serial);
	
	public Map<Device, DeviceStatus> getConnectedDevices();
	
	public List<DeviceStatus> getDeviceStatuses(String serial, DateTime from, DateTime to);
}
