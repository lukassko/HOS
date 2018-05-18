package com.app.hos.service.exceptions.handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.app.hos.service.AbstractMapFactory;
import com.app.hos.service.exceptions.handler.instance.DefaultExceptionHandler;
import com.app.hos.utils.ReflectionUtils;

@SuppressWarnings("rawtypes")
@Service
public class HOSExceptionHandlerFactory implements AbstractMapFactory<Class<? extends Throwable> , HOSExceptionHandler<? extends Throwable> , HOSExceptionHandlerInfo>{

	private HOSExceptionHandler defaultExceptionHandler = new DefaultExceptionHandler();
	
	private final Map<Class<? extends Throwable>, HOSExceptionHandler<? extends Throwable>> handlers = new LinkedHashMap<>();
	
	public void setDefaultExceptionHandler(HOSExceptionHandler defaultExceptionHandler) {
		this.defaultExceptionHandler = defaultExceptionHandler;
	}
	
	public HOSExceptionHandler getDefaultExceptionHandler() {
		return this.defaultExceptionHandler;
	}

	@Override
	public HOSExceptionHandlerInfo get(Class<? extends Throwable> key) {
		HOSExceptionHandler<? extends Throwable> handler = handlers.get(key);
		if (handler != null) {
			HOSExceptionHandlerInfo handlerInfo = new HOSExceptionHandlerInfo();
			handlerInfo.setExceptionClass(key);
			handlerInfo.setHandler(handler);
			return handlerInfo;
		}
		return null;
	}

	public <T extends Throwable> HOSExceptionHandlerInfo get(T throwable) {
		HOSExceptionHandlerInfo handlerInfo = get(throwable.getClass());
		if (handlerInfo != null) {
			handlerInfo.setExcpetion(throwable);
		}
		if (handlerInfo == null && throwable.getCause() != null) {
			handlerInfo = get(throwable.getCause());
		}
		return handlerInfo;
	}
	
	@Override
	public void add(Class<? extends Throwable> key, HOSExceptionHandler<? extends Throwable> value) {
		this.handlers.put(key, value);
	}

	@Override
	public void register(String path) {
		List<String> handlers =  ReflectionUtils.scanForAnnotation(ExceptionHandler.class,path);
		for(String handler : handlers) {
			try {
				Class<?> hanlderClazz = ReflectionUtils.getClass(handler);
				HOSExceptionHandler excpetionHandler = (HOSExceptionHandler)ReflectionUtils.getObjectFromContext(hanlderClazz);
				Class exceptoinClazz = ReflectionUtils.getGenericParamter(handler);
				add(exceptoinClazz, excpetionHandler);
			} catch (BeansException e) {}
			
		}
		
	}


}
