package com.tp.serviceley.server.model.enums;

public enum CookingSpecialities {
    NorthIndian("northIndian"),
    SouthIndian("southIndian"),
    Bengali("bengali"),
    Punjabi("punjabi"),
    Gujrati("gujrati"),
    Marathi("marathi"),
    Odia("odia"),
    Bihari("bihari");

    private String type;

    CookingSpecialities(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
