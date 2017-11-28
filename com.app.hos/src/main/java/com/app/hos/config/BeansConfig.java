package com.app.hos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.app.hos.service.websocket.command.builder.concretebuilders.GetAllDevicesWebCommandBuilder;
import com.app.hos.service.websocket.command.builder.concretebuilders.RemoveDeviceWebCommandBuilder;

@Configuration
@Profile("!web-integration-test")
public class BeansConfig {

	@Bean("allDeviceBuilder")
	@Profile("!web-integration-test")
	@Scope("prototype")
	public GetAllDevicesWebCommandBuilder getAllDevicesWebCommandBuilder() {
	    return new GetAllDevicesWebCommandBuilder();
	}
	
	@Bean("removeDeviceBuilder")
	@Profile("!web-integration-test")
	@Scope("prototype")
	public RemoveDeviceWebCommandBuilder getRemoveDevicesWebCommandBuilder() {
	    return new RemoveDeviceWebCommandBuilder();
	}
}
