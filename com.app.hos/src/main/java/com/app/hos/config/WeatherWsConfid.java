package com.app.hos.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import com.app.hos.service.webservices.RestClient;
import com.app.hos.service.webservices.SoapClient;

@Configuration
public class WeatherWsConfid {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.app.hos.service.webservices.wsdl");
		return marshaller;
	}

	@Bean
	public SoapClient quoteClient(Jaxb2Marshaller marshaller) {
		SoapClient client = new SoapClient();
		client.setDefaultUri("http://www.webservicex.net/globalweather.asmx");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		//return builder.build();
		 RestTemplate restTemplate =  new RestTemplate();
		 restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		 return restTemplate;
	}

	
	@Bean
	public RestClient restClient() {
		return new RestClient();
	}
	
}
