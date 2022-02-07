package com.tp.serviceley.server.model.enums;

public enum Religion {
    Hindu("hindu"),
    Muslim("muslim"),
    Christian("christian"),
    Sikh("sikh"),
    Jain("jain"),
    Buddhist("buddhist"),
    Parsi("parsi"),
    Judaism("judaism"),
    Other("other");

    private String type;

    Religion(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
