package com.app.hos.persistance.models.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.app.hos.persistance.models.BaseEntity;
import com.app.hos.utils.security.SecurityUtils;

import javax.persistence.JoinColumn;

@SuppressWarnings("serial")
@Table(name = "users")
@Entity
public class User extends BaseEntity  {

	@Column(name="name")
	private String name;
	
	@Column(name="hash")
	private String hash;
	
	@Column(name="salt")
	private String salt;

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(
		name = "users_roles", 
		joinColumns = { @JoinColumn(name = "user_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "role_id") }
	)
	private Set<Role> roles = new HashSet<>();

	public User(){}
	
	public User(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addRole(Role role) {
		this.getRoles().add(role);
		role.getUsers().add(this);
	}
	
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public void setPassword(String password) {
		String salt = SecurityUtils.getRandomAsString();
		this.hash = SecurityUtils.hash(password + salt);
		this.salt = salt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((salt == null) ? 0 : salt.hashCode());
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
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (salt == null) {
			if (other.salt != null)
				return false;
		} else if (!salt.equals(other.salt))
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
		return "User [name=" + name + "]";
	}
}
