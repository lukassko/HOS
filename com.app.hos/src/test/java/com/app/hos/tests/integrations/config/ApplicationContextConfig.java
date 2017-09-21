package com.app.hos.tests.integrations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import com.app.hos.service.managers.connection.ConnectionManager;
import com.app.hos.service.managers.device.DeviceManager;
import com.app.hos.service.websocket.command.GetAllDevicesWebCommandBuilder;
import com.app.hos.service.websocket.command.RemoveDeviceWebCommandBuilder;
import com.app.hos.service.websocket.command.WebCommandFactory;
import com.app.hos.service.websocket.command.builder.WebCommand;
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
	
	@Bean
    public AutoInjectDependecyAspect autoInjectDependecyAspect() {
        return new AutoInjectDependecyAspect();
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
