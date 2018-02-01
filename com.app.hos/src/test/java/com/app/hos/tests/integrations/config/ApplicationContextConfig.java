package com.app.hos.tests.integrations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import com.app.hos.service.SystemFacade;
import com.app.hos.service.SystemFacadeImpl;
import com.app.hos.service.integration.server.Server;
import com.app.hos.service.managers.ConnectionManager;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.service.websocket.command.WebCommandFactory;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.builder.concretebuilders.GetAllDevicesWebCommandBuilder;
import com.app.hos.service.websocket.command.builder.concretebuilders.RemoveDeviceWebCommandBuilder;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.utils.aspect.AutoInjectDependecyAspect;

@Configuration
@ComponentScan("com.app.hos.service.websocket.*")
@EnableAspectJAutoProxy(proxyTargetClass=true)
//@EnableSpringConfigured
//@EnableLoadTimeWeaving(aspectjWeaving=AspectJWeaving.ENABLED)
public class ApplicationContextConfig {

	@Bean
    public DeviceManager deviceManager() {
        return new DeviceManager();
    }

	@Bean
    public ConnectionManager connectionManager() {
        return new ConnectionManager();
    }
	
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
