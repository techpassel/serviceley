package com.tp.serviceley.server.model.enums;

public enum OfferAmountType {
    Rupee("rupee"),
    Percentage("percentage");

    private String type;

    OfferAmountType(String type){
        this.type = type;
    }

    String getType(){
        return type;
    }
}
