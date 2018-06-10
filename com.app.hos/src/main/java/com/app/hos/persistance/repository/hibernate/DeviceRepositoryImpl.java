package com.app.hos.persistance.repository.hibernate;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.persistance.models.device.Device;
import com.app.hos.persistance.models.device.DeviceTypeEntity;
import com.app.hos.persistance.repository.DeviceRepository;
import com.app.hos.share.command.type.DeviceType;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

	@PersistenceContext
	private EntityManager manager;
	
	// save api
	
	@Override
	public void save(DeviceTypeEntity type) {
		saveEntity(type);
	}
	
	@Override
	public void save(Device device) {
		saveEntity(device);
	}

	private <T extends BaseEntity> void saveEntity(T entity) {
		if(entity.isNew()) {
			manager.persist(entity);
		} else {
			manager.merge(entity);
		}
	}
	
	// find api
	public Device find(int id) {
		return manager.find(Device.class, id);
	}
	
	@Override
	public DeviceTypeEntity findType(int id) {
		return manager.find(DeviceTypeEntity.class, id);
	}

	@Override
	public DeviceTypeEntity findType(DeviceType type) throws NoResultException, NonUniqueResultException {
		TypedQuery<DeviceTypeEntity> query = manager.createQuery("SELECT dt FROM DeviceTypeEntity dt WHERE dt.type = :type",DeviceTypeEntity.class);
		query.setParameter("type", type);
		return query.getSingleResult();
	}
	
	public Device find(String serial) {
		TypedQuery<Device> query = manager.createQuery("SELECT d FROM Device d WHERE d.serial = :serial", Device.class);
		return query.setParameter("serial", serial).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Device> findAll() {
		Query query = this.manager.createQuery("SELECT d FROM Device d");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Collection<Device> findAllTypes() {
		Query query = this.manager.createQuery("SELECT dt FROM DeviceTypeEntity dt");
		return query.getResultList();
	}
	
	// update api
	public void updateDeviceNameByDeviceId(int id, String name) {
		Device device = find(id);
		device.setName(name);
	}

	public void updateDeviceNameBySerialNo(String serial, String name) {
		Device device = find(serial);
		device.setName(name);
	}
	
	//remove api
	//remove device and all associated data such as statuses and connection
	public void remove(Device device) {
		if (device.isNew()) return;
		if (!manager.contains(device))
			device = manager.merge(device);
		manager.remove(device);
	}

}
