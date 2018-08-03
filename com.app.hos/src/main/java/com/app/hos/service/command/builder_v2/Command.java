package com.app.hos.service.command.builder_v2;

import java.io.Serializable;

import com.app.hos.service.command.result.Result;
import com.app.hos.service.command.type.CommandType;

// GENERIC TYPE -> public class Command<T extends Serializable> implements Serializable {...}
public class Command implements Serializable {

    private static final long serialVersionUID = 3L;
    private CommandType commandType;
    private Result result;
    private boolean status;

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
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

}
