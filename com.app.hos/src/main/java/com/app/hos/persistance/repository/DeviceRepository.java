package com.app.hos.persistance.repository;

import java.util.Collection;

import com.app.hos.persistance.models.Device;

public interface DeviceRepository {

	public void save(Device device);
	
	public Collection<Device> findAll();
	
	public void remove(int id);
	
	public Device findBySerialNumber(String serial);
	
	public Device find(int id);
	
	public void updateDeviceNameByDeviceId(int id,String name);
	
	public void updateDeviceNameBySerialNo(String serial,String name);

}
