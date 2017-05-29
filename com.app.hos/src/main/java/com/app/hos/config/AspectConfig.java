package com.app.hos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

import com.app.hos.utils.aspect.LoggingAspect;
import com.app.hos.utils.aspect.PersistanceAspect;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

	@Bean
    public PersistanceAspect persistanceAspect() {
        return new PersistanceAspect();
    }
	
	@Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
