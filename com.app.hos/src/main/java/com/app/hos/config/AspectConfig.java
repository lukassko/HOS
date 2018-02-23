package com.app.hos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

import com.app.hos.logging.repository.LoggingRepository;
import com.app.hos.service.aspects.ConnectionAspect;
import com.app.hos.service.aspects.ExceptionAspect;
import com.app.hos.service.aspects.PersistanceAspect;

@Profile("!web-integration-test")
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AspectConfig {

	@Bean
    public PersistanceAspect persistanceAspect(LoggingRepository repository) {
        return new PersistanceAspect(repository);
    }
	
	@Bean
    public ConnectionAspect loggingAspect(LoggingRepository repository) {
        return new ConnectionAspect(repository);
    }
	

	@Bean
    public ExceptionAspect exceptionAspect(LoggingRepository repository) {
        return new ExceptionAspect(repository);
    }
}
