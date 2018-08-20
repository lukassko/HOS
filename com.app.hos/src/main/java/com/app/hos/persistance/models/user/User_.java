package com.app.hos.persistance.models.user;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public class User_ {

	public static volatile SingularAttribute<User, String> name;
	
	public static volatile SetAttribute<User, Role> roles;
}
