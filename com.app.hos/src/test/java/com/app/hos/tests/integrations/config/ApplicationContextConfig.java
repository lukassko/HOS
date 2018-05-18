package com.app.hos.tests.integrations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.app.hos.service.api.SystemFacade;
import com.app.hos.service.api.SystemFacadeImpl;
import com.app.hos.service.aspects.AutoInjectDependecyAspect;
import com.app.hos.service.integration.server.Server;
import com.app.hos.service.managers.ConnectionManager;
import com.app.hos.service.managers.DeviceManager;
import com.app.hos.service.websocket.command.WebCommandType;
import com.app.hos.service.websocket.command.builder.WebCommandFactory;
import com.app.hos.service.websocket.command.builder.concretebuilders.RemoveDeviceWebCommandBuilder;
import com.app.hos.service.websocket.command.builder_v2.WebCommand;
import com.app.hos.service.websocket.command.builder_v2.concretebuilders.GetAllDevicesWebCommandBuilder;

@Configuration
@ComponentScan("com.app.hos.service.websocket.*")
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableTransactionManagement
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
