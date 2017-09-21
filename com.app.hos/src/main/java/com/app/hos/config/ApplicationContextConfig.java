package com.app.hos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

import com.app.hos.service.websocket.command.GetAllDevicesWebCommandBuilder;
import com.app.hos.service.websocket.command.RemoveDeviceWebCommandBuilder;

@Configuration
@ComponentScan("com.app.hos.*")
@ImportResource("classpath:/integration/spring-integration.xml")
public class ApplicationContextConfig {


	@Bean("allDeviceBuilder")
	@Scope("prototype")
	public GetAllDevicesWebCommandBuilder getAllDevicesWebCommandBuilder() {
	    return new GetAllDevicesWebCommandBuilder();
	}
	
	@Bean("removeDeviceBuilder")
	@Scope("prototype")
	public RemoveDeviceWebCommandBuilder getRemoveDevicesWebCommandBuilder() {
	    return new RemoveDeviceWebCommandBuilder();
	}
}
