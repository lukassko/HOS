package com.app.hos.service.webservices;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.app.hos.service.webservices.wsdl.GetWeather;
import com.app.hos.service.webservices.wsdl.GetWeatherResponse;

public class SoapClient extends WebServiceGatewaySupport{

	public GetWeatherResponse getWeather(String country, String city) {
		
		GetWeather request = new GetWeather();
		request.setCityName(city);
		request.setCountryName(country);
		GetWeatherResponse response = (GetWeatherResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://www.webservicex.net/globalweather.asmx",request);

		return response;
	}
}
