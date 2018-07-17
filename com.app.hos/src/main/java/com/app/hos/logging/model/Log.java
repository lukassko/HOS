package com.app.hos.logging.model;

import java.util.Date;
import java.util.logging.Level;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//immutable object
@Table(name = "log")
@Entity
public class Log  {
	
	// TODO: Transaction deadlock due to creating next connection for incrementing entity id (executing in separate transaction)
	// TODO: change policy to generate id
	@Id
	@GeneratedValue
	private Integer id;

	@Column(name="time")
	private Date time;
	
	@Column(name="serial")
	private String serial;
	
	@Column(name="level")
	private String level;
	
	@Column(name="message")
	private String message;

	public Log () {}
	
	public Log (String level,String serial,String message) {
		this.time = new Date();
		this.level = level;
		this.serial = serial;
		this.message = message;
	}
	
	public Log (Level level,String serial,String message) {
		this.time = new Date();
		this.level = level.getName();
		this.serial = serial;
		this.message = message;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Date getTime() {
		return time;
	}

	public String getLevel() {
		return level;
	}

	public Level getObjectLevel() {
		return Level.parse(level);
	}
	
	public String getSerial() {
		return serial;
	}

	public String getMessage() {
		return message;
	}

	@Embeddable
	public class DeviceLogTime {
		
	}
}
