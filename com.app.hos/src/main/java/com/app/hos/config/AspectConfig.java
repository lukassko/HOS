package com.app.hos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.app.hos.persistance.logging.LoggingRepository;
import com.app.hos.utils.aspect.LoggingAspect;
import com.app.hos.utils.aspect.PersistanceAspect;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

	@Bean
    public PersistanceAspect persistanceAspect(LoggingRepository repository) {
        return new PersistanceAspect(repository);
    }
	
	@Bean
    public LoggingAspect loggingAspect(LoggingRepository repository) {
        return new LoggingAspect(repository);
    }
}
