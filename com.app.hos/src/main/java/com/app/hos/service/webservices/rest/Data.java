package com.app.hos.service.webservices.rest;

public class Data {

	private String location;
	private double temperature;
	private String skytext;
	private double humidity;
	private String wind;
	private String date;
	private String day;
	
	public Data() {};
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public String getSkytext() {
		return skytext;
	}
	public void setSkytext(String skytext) {
		this.skytext = skytext;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}

}
