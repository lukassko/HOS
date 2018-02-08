package com.app.hos.service.websocket.command.decorators;

import com.app.hos.pojo.WebDeviceStatusesRequest;
import com.app.hos.service.websocket.command.builder.WebCommand;
import com.app.hos.share.utils.DateTime;
import com.app.hos.utils.json.JsonConverter;


// This commands is passed through HTTP request
// DO NOT USE WEBSOCKET
public class GetDeviceStatusesWebCommand extends FutureWebCommandDecorator {
	
	public GetDeviceStatusesWebCommand(WebCommand command) {
		super(command);
	}
	
	public WebCommand call() throws Exception {
		WebDeviceStatusesRequest request = JsonConverter.getObject(command.getMessage(), WebDeviceStatusesRequest.class);
		DateTime from = request.getFrom();
		DateTime to = request.getTo();
		
		// use facade to get statuses
		// convert List to JSON
		// set nessage and convert command to json object
		return null;
	}

}
