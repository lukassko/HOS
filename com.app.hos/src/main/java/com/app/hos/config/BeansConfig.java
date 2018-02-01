package com.app.hos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.builder.concretebuilders.RemoveDeviceWebCommandBuilder;
import com.app.hos.service.websocket.command.decorators.GetAllDeviceWebCommand;
import com.app.hos.service.websocket.command.decorators.GetDeviceStatusesWebCommand;
import com.app.hos.service.websocket.command.decorators.RemoveDeviceWebCommand;
import com.app.hos.utils.ApplicationContextProvider;

@Configuration
@Profile("!web-integration-test")
public class BeansConfig {

	@Bean("allDeviceFutureCommand")
	@Profile("!web-integration-test")
	@Scope("prototype")
	public GetAllDeviceWebCommand getAllDevicesWebCommand(WebCommand command) {
	    return new GetAllDeviceWebCommand(command);
	}
	
	@Bean("deviceStatusesFutureCommand")
	@Profile("!web-integration-test")
	@Scope("prototype")
	public GetDeviceStatusesWebCommand getDeviceStatusesWebCommand(WebCommand command) {
	    return new GetDeviceStatusesWebCommand(command);
	}
	
	@Bean("removeDeviceFutureCommand")
	@Profile("!web-integration-test")
	@Scope("prototype")
	public RemoveDeviceWebCommand getRemoveDevicesWebCommand(WebCommand command) {
	    return new RemoveDeviceWebCommand(command);
	}

	@Bean("applicationContext")
	@Profile("!web-integration-test")
	public ApplicationContextProvider getApplicationContext() {
	    return new ApplicationContextProvider();
	}
}
