package com.tp.serviceley.server.model.enums;

public enum StaffRole {
    CustomerServiceExecutive("Customer Service Executive"),
    ServiceProviderManagementExecutive("Service Provider Management Executive"),
    TechnicalSupportExecutive("Technical Support Executive"),
    Manager("Manager");

    private String type;

    StaffRole(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
