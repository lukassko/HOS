package com.app.hos.persistance.repository;

import com.app.hos.persistance.models.user.User;

public interface UserRepository {
	
	public void save(User user);
	
	public User find(int id);
	
	public User findByName(String userName);
	
	public Users findAll();
}
