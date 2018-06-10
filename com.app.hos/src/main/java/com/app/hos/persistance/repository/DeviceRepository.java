package com.app.hos.persistance.repository;

import java.util.Collection;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.share.command.type.DeviceType;

public interface DeviceRepository {

	public void save(DeviceTypeEntity type);
	
	public void save(Device device);

	public Device find(int id);

	public Device find(String serial);
	
	public DeviceTypeEntity findType(int id);
	
	public DeviceTypeEntity findType(DeviceType type) throws NoResultException, NonUniqueResultException;
	
	public Collection<Device> findAll();
	
	public void remove(Device device);
	
	public void updateDeviceNameByDeviceId(int id,String name);
	
	public void updateDeviceNameBySerialNo(String serial,String name);

}
