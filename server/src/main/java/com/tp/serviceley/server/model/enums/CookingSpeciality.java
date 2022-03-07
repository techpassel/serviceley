package com.tp.serviceley.server.model.enums;

public enum CookingSpeciality {
    NorthIndian("northIndian"),
    SouthIndian("southIndian"),
    Bengali("bengali"),
    Punjabi("punjabi"),
    Gujrati("gujrati"),
    Marathi("marathi"),
    Odia("odia"),
    Bihari("bihari");

    private String type;

    CookingSpeciality(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
