package com.app.hos.persistance.repository.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	@Override
	public Device find(int id) {
		return manager.find(Device.class, id);
	}

	@Override
	public Device find(String serial) {
		try {
			TypedQuery<Device> query = manager.createQuery("SELECT d FROM Device d WHERE d.serial = :serial", Device.class);
			return query.setParameter("serial", serial).getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	@Override
	public DeviceTypeEntity findType(int id) {
		return manager.find(DeviceTypeEntity.class, id);
	}

	@Override
	public DeviceTypeEntity findType(DeviceType type) {
		try {
			TypedQuery<DeviceTypeEntity> query = manager.createQuery("SELECT dt FROM DeviceTypeEntity dt WHERE dt.type = :type",DeviceTypeEntity.class);
			return query.setParameter("type", type).getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<Device> findAll() {
		Query query = this.manager.createQuery("SELECT d FROM Device d");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceTypeEntity> findAllTypes() {
		Query query = this.manager.createQuery("SELECT dt FROM DeviceTypeEntity dt");
		return query.getResultList();
	}

	//remove api
	//remove device and all associated data such as statuses and connection
	@Override
	public void remove(Device device) {
		if (device.isNew()) return;
		if (!manager.contains(device))
			device = manager.merge(device);
		manager.remove(device);
	}

	@Override
	public Device findByConnection(String connectionId) {
		try {
			String queryStr = "SELECT d FROM Device d LEFT JOIN d.connection c WHERE c.connectionId = :connectionId";
			TypedQuery<Device> query = manager.createQuery(queryStr, Device.class);
			return query.setParameter("connectionId", connectionId).getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
}
