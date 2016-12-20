package com.app.hos.model;

import java.io.Serializable;

public class ClientInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public ClientInformation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
