package com.app.hos.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

public class Utils {

    public static double generateRandomDouble() {
    	double generated = ThreadLocalRandom.current().nextDouble(0, 100);
    	generated = generated*100;
    	generated = (double)((int) generated);
		return generated /100;
	}

   // public static Object getObjectFromContext(String beanName) {
    //	return ApplicationContextProvider.getApplicationContext().getBean(beanName);
   // }
    
    public static Object getObjectFromContext(String beanName, Object...bean) {
    	return ApplicationContextProvider.getApplicationContext().getBean(beanName,bean);
    }
    
    public static <T> T getObjectFromContext(Class<T> bean) {
    	return ApplicationContextProvider.getApplicationContext().getBean(bean);
    }

    public static Class<?> getClass(final String clazzName) {
		try {
			return Class.forName(clazzName);
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
    }
    
	public static Class<?> getGenericParamter(String clazzName) {
		ParameterizedType parameterizedType = (ParameterizedType) Utils.getClass(clazzName).getGenericInterfaces()[0];
		return (Class<?>)(parameterizedType.getActualTypeArguments()[0]);
    }

    public static List<String> scanForAnnotation(Class<? extends Annotation> clazz, String basePackage) {
    	String [] packages = {basePackage};
    	return scanForAnnotation(clazz,packages);
    }
    
    public static List<String> scanForAnnotation(Class<? extends Annotation> clazz, String [] packages) {
    	return scanFor(new AnnotationTypeFilter(clazz),clazz,packages);
    }
    
    public static List<String> scanForInterface(Class<?> clazz, String basePackage) {
    	String [] packages = {basePackage};
    	return scanForInterface(clazz,packages);
    }
    
    public static List<String> scanForInterface(Class<?> clazz, String[] basePackage) {
    	return scanFor(new AssignableTypeFilter(clazz),clazz,basePackage);
    }
    
    //AnnotationTypeFilter: filters based on annotations on the class.
    //AssignableTypeFilter: filters that match based on superclass or interface.
    //RegexPatternTypeFilter: matches a fully qualified class name against a regular expression.
    private static List<String> scanFor(TypeFilter includeFilter,Class<?> clazz, String[] basePackage) {
    	List<String> foundClazz = new LinkedList<>();
    	final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
    	scanner.setResourceLoader(new PathMatchingResourcePatternResolver(Thread.currentThread().getContextClassLoader()));
		scanner.addIncludeFilter(includeFilter);
		for (String pck : basePackage) {
			for (BeanDefinition bd : scanner.findCandidateComponents(pck)) {
				foundClazz.add(bd.getBeanClassName());
			}
		}
		return foundClazz;
    }

}
