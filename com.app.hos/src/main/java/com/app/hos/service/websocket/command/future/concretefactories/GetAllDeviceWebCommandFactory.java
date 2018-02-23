package com.app.hos.service.websocket.command.future.concretefactories;

import java.util.concurrent.Callable;

import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.service.websocket.command.decorators.GetAllDeviceWebCommand;
import com.app.hos.service.websocket.command.future.FutureCommand;
import com.app.hos.service.websocket.command.future.WebCommandFactory;
import com.app.hos.service.websocket.command.type.WebCommandType;
import com.app.hos.utils.Utils;

@FutureCommand(type = WebCommandType.GET_ALL_DEVICES)
public class GetAllDeviceWebCommandFactory implements WebCommandFactory {

	@Override
	public Callable<WebCommand> get(WebCommand command) {
		return (GetAllDeviceWebCommand)Utils.getObjectFromContext("allDeviceFutureCommand",command);
	}

}
