package com.app.hos.service.webservices;

import org.springframework.web.client.RestTemplate;

import com.app.hos.service.webservices.rest.MainClass;

public class RestClient {
	
	public RestClient() {}
	
	public MainClass getData(String arg) {
		RestTemplate restTemplate = new RestTemplate();
		MainClass mainClass = restTemplate.getForObject("http://weathers.co/api.php", MainClass.class);
		return mainClass;
	}
}
