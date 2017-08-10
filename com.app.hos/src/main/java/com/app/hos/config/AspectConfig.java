package com.app.hos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.app.hos.logging.LoggingRepository;
import com.app.hos.utils.aspect.ConnectionAspect;
import com.app.hos.utils.aspect.PersistanceAspect;

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
}
