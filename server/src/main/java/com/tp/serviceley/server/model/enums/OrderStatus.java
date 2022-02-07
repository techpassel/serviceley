package com.tp.serviceley.server.model.enums;

public enum OrderStatus {
    Created("created"),
    Approved("approved"),
    Declined("declined"),
    Cancelled("cancelled"),
    Active("active"),
    Completed("completed");

    private String type;

    OrderStatus(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
