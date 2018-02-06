package com.app.hos.share.command.builder;

import com.app.hos.share.command.result.Result;
import com.app.hos.share.command.type.CommandType;

import java.io.Serializable;


// GENERIC TYPE -> public class Command<T extends Serializable> implements Serializable {...}
public class Command implements Serializable {

    private static final long serialVersionUID = 2L;
    private String serialId;
    private String commandType;
    private Result result;
    private boolean status;

	public void setSerialId (String serialId) {
        this.serialId = serialId;
    }

    public String getSerialId() {
        return serialId;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public Result getResult() {
        return result;
    }
    
    public void setResult(Result result) {
        this.result = result;
    }
    
    public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
    public CommandType getEnumeratedCommandType() {
    	return CommandType.valueOf(commandType);
    }
}
