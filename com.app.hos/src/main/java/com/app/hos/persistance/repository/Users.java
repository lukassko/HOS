package com.app.hos.persistance.repository;

import com.app.hos.persistance.models.user.Role.UserRole;
import com.app.hos.persistance.models.user.User;

public interface Users extends Iterable<User> {

	public Users hasRole(UserRole role);
	
	public Users activeOnly();
}
