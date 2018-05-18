package com.app.hos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.hos.service.websocket.command.builder_v2.WebCommand;
import com.app.hos.service.websocket.command.decorators.GetAllDeviceWebCommand;
import com.app.hos.utils.ApplicationContextProvider;

@Configuration
@Profile("!web-integration-test")
public class BeansConfig {

	//@Bean("allDeviceFutureCommand")
	//@Profile("!web-integration-test")
	//@Scope("prototype")
	public GetAllDeviceWebCommand getAllDevicesWebCommand(WebCommand command) {
	    return new GetAllDeviceWebCommand(command);
	}

	@Bean("applicationContext")
	@Profile("!web-integration-test")
	public ApplicationContextProvider getApplicationContext() {
	    return new ApplicationContextProvider();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
