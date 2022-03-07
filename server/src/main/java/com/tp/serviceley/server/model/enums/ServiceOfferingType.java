package com.tp.serviceley.server.model.enums;

public enum ServiceOfferingType {
    Main("main"),
    Additional("additional");

    private String type;

    ServiceOfferingType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
