package com.app.hos.persistance.models;

import javax.persistence.Entity;

@Entity
public class BaseEntity {

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
