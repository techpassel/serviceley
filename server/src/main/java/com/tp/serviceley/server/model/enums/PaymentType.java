package com.tp.serviceley.server.model.enums;

public enum PaymentType {
    OneTime("oneTime"),
    Monthly("monthly");

    private String type;

    PaymentType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
