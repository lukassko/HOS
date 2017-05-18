package com.app.hos.persistance.repository;

import java.util.Collection;

import com.app.hos.persistance.models.Device;

public interface DeviceRepository {

	public void save(Device device);
	
	public Collection<Device> findAll();
}
