package com.app.hos.persistance.models;

import javax.persistence.Entity;
import javax.persistence.Id;

//@Entity
public class BaseEntity {

	@Id
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
