package com.app.hos.share.command.result;

import java.io.Serializable;

public class NewDevice implements Result, Serializable {

    private String name;

    public NewDevice(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
