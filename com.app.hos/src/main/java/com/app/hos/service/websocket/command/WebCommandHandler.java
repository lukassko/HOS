package com.app.hos.service.websocket.command;

import java.util.concurrent.Callable;

import com.app.hos.service.websocket.command.builder.WebCommand;

// USE BlockingQueue ; stworzyc dodatkowy watek odbierajacy dane z kolejki i wysylajece spowrotem na WWW

public class WebCommandHandler implements Callable<WebCommand> {


	public WebCommand call() throws Exception {

		return null;
	}

}
