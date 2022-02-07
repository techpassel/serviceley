package com.tp.serviceley.server.model.enums;

public enum ServiceUnitMeasure {
    SquareFeet("squareFeet"),
    PerPerson("perPerson"),
    PerBathroom("perBathroom");

    private String type;

    ServiceUnitMeasure(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
