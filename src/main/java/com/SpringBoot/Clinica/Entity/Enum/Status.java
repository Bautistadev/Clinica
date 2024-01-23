package com.SpringBoot.Clinica.Entity.Enum;

public enum Status {
    ENABLE("ENABLE"),DISABLE("DISABLE"),SUSPENDED("SUSPENDED");

    private String status;

    private Status(String status){
        this.status = status;
    }

    public String getStatusName(){
        return this.status.toString();
    }
}
