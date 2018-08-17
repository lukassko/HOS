package com.app.hos.persistance.repository;

import java.util.List;

import com.app.hos.persistance.models.user.Role.UserRole;
import com.app.hos.persistance.models.user.User;

public interface Users extends Iterable<User> {

	public Users hasRole(UserRole role);

	public List<User> getAsLIst();
}
