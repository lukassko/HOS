package com.app.hos.persistance.repository.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.app.hos.persistance.models.command.CommandTypeEntity;
import com.app.hos.persistance.repository.CommandRepository;

@Repository
public class CommandRepositoryImpl implements CommandRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void save(CommandTypeEntity type) {
		if(type.isNew()) {
			manager.persist(type);
		} else {
			manager.merge(type);
		}
	}
}
