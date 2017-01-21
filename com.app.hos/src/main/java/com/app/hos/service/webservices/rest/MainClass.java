package com.app.hos.service.webservices.rest;

public class MainClass {

	private String apiVersion;
	private Data data;
	
	public MainClass(){};
	
	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
}
