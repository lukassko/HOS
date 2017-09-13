package com.app.hos.tests.integrations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.app.hos.service.managers.connection.ConnectionManager;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.service.websocket.command.WebCommandFactory;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.type.WebCommandType;

@Configuration
@ComponentScan("com.app.hos.service.websocket.*")
public class ApplicationContextConfig {

	@Bean
    public DeviceManager deviceManager() {
        return new DeviceManager();
    }

	@Bean
    public ConnectionManager connectionManager() {
        return new ConnectionManager();
    }
	

//	@Bean
//	@Scope("prototype")
//	public WebCommand getCommand(WebCommandType type) {
//		WebCommandFactory facotry = new WebCommandFactory();
//	    return facotry.getCommand(type);
//	}
}
