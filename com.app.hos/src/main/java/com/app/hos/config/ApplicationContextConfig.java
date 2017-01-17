package com.app.hos.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("com.app.hos.*")
@ImportResource("classpath:/integration/spring-integration.xml")
public class ApplicationContextConfig {


}
