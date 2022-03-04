package com.tp.serviceley.server.model.enums;

public enum UserType {
    User("user"),
    Admin("admin"),
    Staff("staff"),
    ServiceProvider("service-provider");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
