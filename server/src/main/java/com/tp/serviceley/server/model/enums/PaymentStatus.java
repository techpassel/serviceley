package com.tp.serviceley.server.model.enums;

public enum PaymentStatus {
    Created("created"),
    Initiated("initiated"),
    Pending("pending"),
    Declined("declined"),
    Completed("completed");

    private String type;

    PaymentStatus(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
