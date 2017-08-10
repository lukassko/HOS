package com.app.hos.persistance.repository;

import java.util.Collection;

import com.app.hos.persistance.models.Connection;
import com.app.hos.persistance.models.Device;

public interface DeviceRepository {

	public void save(Device device);
	
	public Collection<Device> findAll();
	
	public Device findDeviceBySerialNumber(String serial);
	
	public Device findDeviceById(int id);
	
	public void updateDeviceNameByDeviceId(int id,String name);
	
	public void updateDeviceNameBySerialNo(String serial,String name);

}
