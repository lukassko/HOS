package com.app.hos.persistance.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class BaseEntity {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	//@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isNew() {
		return (this.id==null);
	}
}
