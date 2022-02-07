package com.tp.serviceley.server.model.enums;

public enum ServiceOfferingType {
    Main("main"),
    Additional("additional"),
    MainAndAdditional("mainAndAdditional");

    private String type;

    ServiceOfferingType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
