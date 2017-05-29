package com.app.hos.utils.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class PersistanceAspect {

	//@Pointcut("within(com.app.hos.persistance.repository.hibernate.*)")  
	//public void testPointcut(){};
	
	//com.xyz.someapp.ChildServiceInterface+
	@Before("within(com.app.hos.persistance.repository.hibernate.DeviceRepository*)")
	public void logTest(JoinPoint joinPoint) {
		System.out.println("Before invoking repository methods: " + joinPoint.toString() );
	}
}
