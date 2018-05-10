package com.app.hos.persistance.repository;

import java.util.Collection;

import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;

public interface DeviceRepository {

	public void save(DeviceTypeEntity type);
	
	public void save(Device device);
	
	public Collection<Device> findAll();
	
	public void remove(Device device);
	
	public Device findBySerialNumber(String serial);
	
	public Device find(int id);
	
	public void updateDeviceNameByDeviceId(int id,String name);
	
	public void updateDeviceNameBySerialNo(String serial,String name);

}
