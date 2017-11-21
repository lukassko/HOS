package com.app.hos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!web-integration-test")
@ImportResource("classpath:/integration/spring-integration.xml")
public class SpringIntegrationConfig {
}
