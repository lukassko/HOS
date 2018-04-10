package com.app.hos.service.exceptions.handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.app.hos.service.exceptions.handler.instance.DefaultExceptionHandler;
import com.app.hos.utils.Utils;

@SuppressWarnings("rawtypes")
@Service
public class HOSExceptionHandlerFactory {

	private HOSExceptionHandler defaultExceptionHandler = new DefaultExceptionHandler();
	
	private final Map<Class<? extends Throwable>, HOSExceptionHandler<? extends Throwable>> handlers = new LinkedHashMap<>();
	
	public void setDefaultExceptionHandler(HOSExceptionHandler defaultExceptionHandler) {
		this.defaultExceptionHandler = defaultExceptionHandler;
	}
	
	public HOSExceptionHandler getDefaultExceptionHandler() {
		return this.defaultExceptionHandler;
	}
	
	public HOSExceptionHandlerInfo getHandler(Class<? extends Throwable> clazz) {
		HOSExceptionHandler<? extends Throwable> handler = handlers.get(clazz);
		if (handler != null) {
			HOSExceptionHandlerInfo handlerInfo = new HOSExceptionHandlerInfo();
			handlerInfo.setExceptionClass(clazz);
			handlerInfo.setHandler(handler);
			return handlerInfo;
		}
		return null;
	}

	public <T extends Throwable> HOSExceptionHandlerInfo getHandler(T throwable) {
		HOSExceptionHandlerInfo handlerInfo = getHandler(throwable.getClass());
		if (handlerInfo != null) {
			handlerInfo.setExcpetion(throwable);
		}
		if (handlerInfo == null && throwable.getCause() != null) {
			handlerInfo = getHandler(throwable.getCause());
		}
		return handlerInfo;
	}
	
	public void addHandler(Class<? extends Throwable> clazz, HOSExceptionHandler<? extends Throwable> handler) {
		this.handlers.put(clazz, handler);
	}
	
	@SuppressWarnings("unchecked")
	public void registerHandlers(String packageToScan){
		List<String> handlers =  Utils.scanForAnnotation(ExceptionHandler.class,packageToScan);
		for(String handler : handlers) {
			try {
				Class<?> hanlderClazz = Utils.getClass(handler);
				HOSExceptionHandler excpetionHandler = (HOSExceptionHandler)Utils.getObjectFromContext(hanlderClazz);
				Class exceptoinClazz = Utils.getGenericParamter(handler);
				addHandler(exceptoinClazz, excpetionHandler);
			} catch (BeansException e) {}
			
		}
	}
}
