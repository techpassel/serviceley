package com.tp.serviceley.server.model;

public enum TokenType {
    AccountActivation("accountActivation"),
    PhoneVerificationOTP("phoneVerificationOTP"),
    EmailUpdateVerification("emailUpdateVerification");

    public String type;

    TokenType(String type) {
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
