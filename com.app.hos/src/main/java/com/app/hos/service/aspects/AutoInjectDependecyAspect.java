package com.app.hos.service.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


@SuppressWarnings("rawtypes")
@Aspect
public class AutoInjectDependecyAspect implements ApplicationContextAware {

    private ApplicationContext  applicationContext = null;

    @Pointcut("@annotation(org.springframework.beans.factory.annotation.Configurable)")
    public void annotationPointCutDefinition(){
    }
 
    @Pointcut("execution(* *(..))")
    public void atExecution(){}
    
    @Before("annotationPointCutDefinition() && atExecution()")
    public void printNewLine(JoinPoint pointcut){
        //Just prints new lines after each method that's executed in
    	System.out.println("EXECUTE");
    }
    
    //@Before( "constructor()" )
    public void injectAutoWiredFields( JoinPoint aPoint ) {
        Class theClass = aPoint.getTarget().getClass();
        try{
            Field[] theFields = theClass.getDeclaredFields();
            for ( Field thefield : theFields ) {
                for ( Annotation theAnnotation : thefield.getAnnotations() ) {
                    if ( theAnnotation instanceof Autowired ) {
                        // found a field annotated with 'AutoWired'
                        if ( !thefield.isAccessible() ) {
                            thefield.setAccessible( true );
                        }

                        Object theBean = applicationContext.getBean( thefield.getType() );
                        if ( theBean != null ) {
                            thefield.set( aPoint.getTarget(), theBean );
                        }
                    }
                }
            }
        } catch ( Exception e ) {
        	e.printStackTrace();
        }

    }

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}