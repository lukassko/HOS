package com.app.hos.persistance.repository.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.app.hos.persistance.models.user.Role;
import com.app.hos.persistance.models.user.Role.UserRole;
import com.app.hos.persistance.models.user.User;
import com.app.hos.persistance.repository.Users;

public class UsersRepositoryImpl implements Users, Cloneable {

	private EntityManager entityManager;

	private UserRole userRole = null;
	
	public UsersRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	private TypedQuery<User> buildQuery() {
		// build query depends on passed arguments such as activeOnly and userRole
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
		Join<User, Role> join = user.join("roles");
		
		// to support multiple predicates
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (userRole != null) {
			predicates.add(cb.equal(join.get("role"), userRole));
		}
		cq.select(user).where(predicates.toArray(new Predicate[]{}));
		return entityManager.createQuery(cq);
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
	public List<User> getAsLIst() {
		return buildQuery().getResultList();
	}

}
