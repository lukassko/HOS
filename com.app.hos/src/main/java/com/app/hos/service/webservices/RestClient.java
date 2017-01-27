package com.app.hos.service.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.app.hos.service.webservices.rest.MainClass;
import com.app.hos.service.webservices.rest.Quote;

public class RestClient {
	
	@Autowired
	RestTemplate restTemplate;
	
	public RestClient() {}
	
	public Quote getData(String arg) {
		//MainClass mainClass = restTemplate.getForObject("http://weathers.co/api.php?city=London", MainClass.class);
		RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		return quote;
	}
}
