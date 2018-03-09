package com.app.hos.persistance.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;

@Entity
public class Role extends BaseEntity {

	public enum UserRole {
		ADMIN, USER
	}
	
	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	public Role(UserRole userRole) {
		this.userRole= userRole;
	}
	
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((userRole == null) ? 0 : userRole.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (userRole != other.userRole)
			return false;
		return true;
	}
	
}
