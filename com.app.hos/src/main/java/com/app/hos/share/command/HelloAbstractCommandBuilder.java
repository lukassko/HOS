package com.app.hos.share.command;

import java.util.LinkedList;
import java.util.List;

import com.app.hos.share.command.builder.AbstractCommandBuilder;
import com.app.hos.share.command.builder.CommandType;
import com.app.hos.share.command.result.NewDevice;
import com.app.hos.share.command.result.Result;

public class HelloAbstractCommandBuilder extends AbstractCommandBuilder {

	  @Override
	    public void setCommandType() {
	        String type = CommandType.HELLO.toString();
	        command.setCommandType(type);
	    }

	    @Override
	    public void setResult() {
	        command.setResult(new LinkedList<Result>());
	    }

}
