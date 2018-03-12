package com.app.hos.persistance.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Table(name = "users")
@Entity
public class User extends BaseEntity  {

	private String name;
	
	private String password;

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(
		name = "users_roles", 
		joinColumns = { @JoinColumn(name = "user_id",referencedColumnName="id") }, 
		inverseJoinColumns = { @JoinColumn(name = "role_id",referencedColumnName="id") }
	)
	private Set<Role> roles;

	public User(){}
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addRole(Role role) {
		this.getRoles().add(role);
		role.getUsers().add(this);
	}
	
	public Set<Role> getRoles() {
		if (this.roles == null) {
			this.roles = new HashSet<>();
		}
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!compareRoles(other))
			return false;
		return true;
	}

	public boolean compareRoles(User other) {
		if (this.getRoles().size() != other.getRoles().size())
			return false;	
		Set<Role> othersRole = other.getRoles();
		Set<Role> thisRole = this.getRoles();
		for (Role role : thisRole) {
			if (!othersRole.contains(role)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + "]";
	}
	

}
