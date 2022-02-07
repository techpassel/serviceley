package com.tp.serviceley.server.model.enums;

public enum ComplainStatus {
    Created("created"),
    Assigned("assigned"),
    Active("active"),
    Resolved("resolved");

    private String type;

    ComplainStatus(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
