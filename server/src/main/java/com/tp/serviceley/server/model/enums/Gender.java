package com.tp.serviceley.server.model.enums;

public enum Gender {
    Male("male"),
    Female("female");

    private String type;

    Gender(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
