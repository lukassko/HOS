package com.app.hos.persistance.models.user;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.app.hos.persistance.models.user.Role.UserRole;

@StaticMetamodel(Role.class)
public class Role_ {

	public static volatile SingularAttribute<Role, UserRole> role;
	
}
