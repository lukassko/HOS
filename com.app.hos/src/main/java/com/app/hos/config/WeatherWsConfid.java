package com.app.hos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.app.hos.service.webservices.WeatherClient;
@
Configuration
public class WeatherWsConfid {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.app.hos.service.webservices.wsdl");
		return marshaller;
	}

	@Bean
	public WeatherClient quoteClient(Jaxb2Marshaller marshaller) {
		WeatherClient client = new WeatherClient();
		client.setDefaultUri("http://www.webservicex.net/globalweather.asmx");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
}
