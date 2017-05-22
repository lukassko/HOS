package com.app.hos.config;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import com.app.hos.service.webservices.RestClient;
import com.app.hos.service.webservices.SoapClient;

@Configuration
public class WeatherWsConfig {

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
		 StringHttpMessageConverter converter = new StringHttpMessageConverter();
		 converter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "html", 
                 Charset.forName("UTF-8"))));
		 
		 restTemplate.getMessageConverters()
	        .add(0, converter);
		 return restTemplate;
	}

	
	@Bean
	public RestClient restClient() {
		return new RestClient();
	}
	
}
