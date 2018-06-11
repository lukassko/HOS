package com.app.hos.persistance.repository.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.app.hos.persistance.models.command.CommandTypeEntity;
import com.app.hos.persistance.repository.CommandRepository;
import com.app.hos.share.command.type.CommandType;

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

	@Override
	public CommandTypeEntity find(int id) {
		return manager.find(CommandTypeEntity.class, id);
	}

	@Override
	public CommandTypeEntity find(CommandType type) {
		try {
			TypedQuery<CommandTypeEntity> query = manager.createQuery("SELECT ct FROM CommandTypeEntity ct WHERE ct.type = :type",CommandTypeEntity.class);
			return query.setParameter("type", type).getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
}
