package com.app.hos.persistance.repository.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.app.hos.persistance.models.user.User;
import com.app.hos.persistance.repository.UserRepository;
import com.app.hos.persistance.repository.Users;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void save(User user) {
		if(user.isNew()) {
			manager.persist(user);
		} else {
			manager.merge(user);
		}
	}
	
	@Override
	public User find(int id) {
		return manager.find(User.class, id);
	}

	@Override
	public User findByName(String name) {
		String queryString = "SELECT u FROM User u WHERE u.name = :name";
		Query query = manager.createQuery(queryString);
		
		query.setParameter("name", name);
		
		return (User) query.getSingleResult();
	}

	@Override
	public Users findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
