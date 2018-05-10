package com.app.hos.persistance.models.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.app.hos.persistance.models.BaseEntity;

@SuppressWarnings("serial")
@Table(name = "roles")
@Entity
public class Role extends BaseEntity {

	public enum UserRole {
		ADMIN, USER
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private UserRole role;

	public Role() {}
	
	public Role(UserRole role) {
		this.role= role;
	}
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<User> users;

	public UserRole getUserRole() {
		return role;
	}

	public void setUserRole(UserRole role) {
		this.role = role;
	}

	public Set<User> getUsers() {
		if (this.users == null) {
			this.users = new HashSet<>();
		}
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		if (role != other.role)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [role= " + role + "]";
	}
	
}
