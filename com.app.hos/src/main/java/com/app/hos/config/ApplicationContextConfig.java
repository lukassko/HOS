package com.app.hos.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ApplicationContextConfig {
	
	@Configuration
	@Profile("!web-integration-test")
	@ComponentScan("com.app.hos*")
    static class NoWebIntegrationTestConfig
    { }
	
	@Configuration
	@Profile("web-integration-test")
	@ComponentScan({"com.app.hos.config*", "com.app.hos.service.websocket*"})
    static class WebIntegrationTestConfig
    { }
}
