package com.app.hos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.app.hos.service.managers.UserManager;

@Configuration
@EnableTransactionManagement
public class ApplicationContextConfig {
	
	@Configuration
	@Profile("!web-integration-test")
	@ComponentScan("com.app.hos*")
    static class NoWebIntegrationTestConfig
    { }
	
	@Configuration
	@Profile("web-integration-test")
	@ComponentScan({
		"com.app.hos.config*", 
		"com.app.hos.service.websocket*",
		"com.app.hos.security*"
	})
    static class WebIntegrationTestConfig
    { 
		@Bean
		public UserManager getUserManager() {
			return new UserManager();
		}
    }
}
