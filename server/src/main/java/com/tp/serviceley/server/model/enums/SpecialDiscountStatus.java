package com.tp.serviceley.server.model.enums;

public enum SpecialDiscountStatus {
    Created("created"),
    Approved("approved"),
    Rejected("rejected"),
    Activated("activated"),
    Expired("expired");

    private String type;

    SpecialDiscountStatus(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
