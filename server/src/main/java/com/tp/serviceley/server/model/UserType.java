package com.tp.serviceley.server.model;

public enum UserType {
    User("user"),
    Admin("admin"),
    Staff("staff");

    public String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

}
