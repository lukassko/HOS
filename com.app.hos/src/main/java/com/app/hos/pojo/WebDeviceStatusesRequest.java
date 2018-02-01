package com.app.hos.pojo;

import com.app.hos.share.utils.DateTime;

/**
 * POJO class to send request over JSON
 *  to get device statuses with defined period
 */
public class WebDeviceStatusesRequest {

	private Integer id;

	private String serial;

	private DateTime from;
	
	private DateTime to;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public DateTime getFrom() {
		return from;
	}

	public void setFrom(DateTime from) {
		this.from = from;
	}

	public DateTime getTo() {
		return to;
	}

	public void setTo(DateTime to) {
		this.to = to;
	}

}