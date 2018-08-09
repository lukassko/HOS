package com.app.hos.persistance.repository.hibernate;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.app.hos.persistance.models.user.Role.UserRole;
import com.app.hos.persistance.models.user.User;
import com.app.hos.persistance.repository.Users;

// TODO test class
public class UsersRepositoryImpl implements Users, Cloneable {

	@PersistenceContext
	private EntityManager entityManager;
	
	private boolean activeOnly = false;
	
	private UserRole userRole;
	
	private TypedQuery<User> buildQuery() {
		// build query depends on passed arguments such as activeOnly and userRole
		return entityManager.createQuery("SELECT u FROM User u",User.class);
	}
	
	@Override
	public Iterator<User> iterator() {
		TypedQuery<User> query = buildQuery();
		return query.getResultList().iterator();
	}

	@Override
	public Users hasRole(UserRole role) {
		try {
			UsersRepositoryImpl copy = (UsersRepositoryImpl) this.clone();
			copy.userRole = role;
			return copy;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Users activeOnly() {
		try {
			UsersRepositoryImpl copy = (UsersRepositoryImpl) this.clone();
			copy.activeOnly = true;
			return copy;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
