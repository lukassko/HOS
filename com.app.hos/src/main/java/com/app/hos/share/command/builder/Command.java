package com.app.hos.share.command.builder;

import com.app.hos.share.command.result.Result;

import java.io.Serializable;
import java.util.List;


// GENERIC TYPE -> public class Command<T extends Serializable> implements Serializable {...}
public class Command implements Serializable{

    private static final long serialVersionUID = 1L;
    private String serialId;
    private String commandType;
    private List<Result> result;

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

    public List<Result> getResult() {
        return result;
    }
    public void setResult(List<Result> result) {
        this.result = result;
    }
}
