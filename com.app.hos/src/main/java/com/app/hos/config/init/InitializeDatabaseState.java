package com.app.hos.config.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.app.hos.service.SystemFacade;

@Component
@Profile("init")
public class InitializeDatabaseState {

	@Autowired
	private SystemFacade systemFacade;
	
	@PostConstruct
    public void init() {
    }
	
}
