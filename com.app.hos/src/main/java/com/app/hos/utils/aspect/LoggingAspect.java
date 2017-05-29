package com.app.hos.utils.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {
	
	@Before("execution(* com.app.hos.utils.json.JsonConverter.*(..))")
	public void logTest(JoinPoint joinPoint) {
		System.out.println("Before invoking JsonConverter methods " + joinPoint.toString());
	}

}
