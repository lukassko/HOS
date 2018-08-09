package com.app.hos.persistance.repository;

import java.util.List;

import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.service.command.type.DeviceType;

public interface DeviceRepository {

	public void save(DeviceTypeEntity type);
	
	public void save(Device device);
	
	public void remove(Device device);
	
	public Device find(int id);

	public Device findBySerial(String serial);

	public Device findByConnection(String connectionId);

	public List<Device> findAll();

	
	// TODO consider to separate repository
	public DeviceTypeEntity findType(int id);
	
	public DeviceTypeEntity findType(DeviceType type);
	
	public List<DeviceTypeEntity> findAllTypes();

}
