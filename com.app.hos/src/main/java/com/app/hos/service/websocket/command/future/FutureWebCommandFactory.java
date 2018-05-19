package com.app.hos.service.websocket.command.future;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.app.hos.service.AbstractMapFactory;
import com.app.hos.service.websocket.command.WebCommandType;
import com.app.hos.service.websocket.command.builder_v2.WebCommand;
import com.app.hos.service.websocket.command.decorators.FutureWebCommandDecorator;
import com.app.hos.utils.ReflectionUtils;

@Service
public class FutureWebCommandFactory 
		implements AbstractMapFactory<Object,String, Callable<WebCommand>>, ApplicationContextAware {
	
	// String as bean name
	private final Map<WebCommandType, String> beans = new LinkedHashMap<>();
	
	private ApplicationContext applicationContext;
	
	@Override
	public Callable<WebCommand> get(Object command) {
		if (!(command instanceof WebCommand))
			throw new IllegalArgumentException();
		
		WebCommand cmd = (WebCommand) command;
		WebCommandType type = cmd.getType(); 
		String beanName = beans.get(type);
		Object obj = ReflectionUtils.getObjectFromContext(beanName,command);
		return (FutureWebCommandDecorator)obj;
	}

	@Override
	public void add(Object key, String value) {
		if (!(key instanceof WebCommandType))
			throw new IllegalArgumentException();
		WebCommandType type = (WebCommandType) key;
		beans.put(type, value);
	}

	@Override
	public void register(String path) {
		List<String> commands =  ReflectionUtils.scanForAnnotation(FutureCommand.class,path);
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
		BeanDefinitionRegistry registry = ((BeanDefinitionRegistry )beanFactory);
		for(String command : commands) {
			try {
				Class<?> clazz = ReflectionUtils.getClass(command);
				FutureCommand annotation = clazz.getAnnotation(FutureCommand.class);
				WebCommandType type = annotation.type();
				String beanName = clazz.getSimpleName();
				
				GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
				beanDefinition.setBeanClass(clazz);
				beanDefinition.setScope("prototype");

				registry.registerBeanDefinition(beanName,beanDefinition);
				add(type,beanName);
			} catch (BeansException e) {}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
