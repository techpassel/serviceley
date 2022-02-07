package com.tp.serviceley.server.model.enums;

public enum TokenType {
    AccountActivation("accountActivation"),
    PhoneVerificationOTP("phoneVerificationOTP"),
    EmailUpdateVerification("emailUpdateVerification");

    private String type;

    TokenType(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
