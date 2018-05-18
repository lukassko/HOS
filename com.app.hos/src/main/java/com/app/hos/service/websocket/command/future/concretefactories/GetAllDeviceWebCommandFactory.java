package com.app.hos.service.websocket.command.future.concretefactories;

import java.util.concurrent.Callable;

import com.app.hos.service.websocket.command.builder_v2.WebCommand;
import com.app.hos.service.websocket.command.decorators.GetAllDeviceWebCommand;
import com.app.hos.service.websocket.command.future.CallableCommandFactory;
import com.app.hos.utils.ReflectionUtils;

//@FutureCommand(type = WebCommandType.GET_ALL_DEVICES)
public class GetAllDeviceWebCommandFactory implements CallableCommandFactory {

	@Override
	public Callable<WebCommand> get(WebCommand command) {
		return (GetAllDeviceWebCommand)ReflectionUtils.getObjectFromContext("allDeviceFutureCommand",command);
	}

}
