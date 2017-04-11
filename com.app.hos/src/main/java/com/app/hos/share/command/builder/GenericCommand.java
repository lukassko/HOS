package com.app.hos.share.command.builder;

import java.io.Serializable;
import java.util.List;

public class GenericCommand<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientName;
    private String commandType;
    private List<T> result;

    public void setClientName (String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public List<T> getResult() {
        return result;
    }
    public void setResult(List<T> result) {
        this.result = result;
    }
}
