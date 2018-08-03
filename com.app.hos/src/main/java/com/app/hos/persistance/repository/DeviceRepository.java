package com.app.hos.persistance.repository;

import java.util.List;

import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.service.command.type.DeviceType;

public interface DeviceRepository {

	public void save(DeviceTypeEntity type);
	
	public void save(Device device);

	public Device find(int id);

	public Device find(String serial);

	public Device findByConnection(String connectionId);
	
	public DeviceTypeEntity findType(int id);
	
	public DeviceTypeEntity findType(DeviceType type);
	
	public List<Device> findAll();
	
	public List<DeviceTypeEntity> findAllTypes();
	
	public void remove(Device device);

}
