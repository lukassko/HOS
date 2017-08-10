package com.app.hos.persistance.repository.hibernate;

import java.util.Collection;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.repository.DeviceRepository;

@Transactional
@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

	@PersistenceContext//(unitName="myslq_persistance")
	private EntityManager manager;
	
	public void save(Device device) {
		if(device.isNew()) {
			manager.persist(device);
		} else {
			manager.merge(device);
		}
	}

	public Device findDeviceById(int id) {
		return manager.find(Device.class, id);
	}
	
	public Device findDeviceBySerialNumber(String serial) {
		TypedQuery<Device> query = manager.createQuery("SELECT d FROM Device d WHERE d.serial = :serial", Device.class);
		return query.setParameter("serial", serial).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Device> findAll() {
		Query query = this.manager.createQuery("SELECT d FROM Device d");
		return query.getResultList();
	}

	public void updateDeviceNameByDeviceId(int id, String name) {
		Device device = findDeviceById(id);
		device.setName(name);
	}

	public void updateDeviceNameBySerialNo(String serial, String name) {
		Device device = findDeviceBySerialNumber(serial);
		device.setName(name);
	}

}
