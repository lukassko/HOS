package com.app.hos.persistance.repository.hibernate;

import java.util.Collection;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.hos.persistance.models.Device;
import com.app.hos.persistance.repository.DeviceRepository;

@Transactional
@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

	@PersistenceContext
	private EntityManager manager;
	
	public void save(Device device) {
		if(device.isNew()) {
			manager.persist(device);
		} else {
			manager.merge(device);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Device> findAll() {
		Query query = this.manager.createQuery("SELECT d FROM Device d");
		return query.getResultList();
	}

}
