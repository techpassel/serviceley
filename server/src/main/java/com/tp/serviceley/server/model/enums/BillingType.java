package com.tp.serviceley.server.model.enums;

public enum BillingType {
    OneTime("oneTime"),
    DailyBasis("dailyBasis"),
    MonthlyBasis("monthlyBasis");

    private String type;

    BillingType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
