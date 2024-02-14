package com.SpringBoot.Clinica.Entity.Enum;

public enum AppointmentStatus {
    CONFIRMED(1,"CONFIRMED"),CANCELED(3,"CANCELED"),COMPLETED(2,"COMPLETED");

    private Integer code;
    private String data;

    private AppointmentStatus(Integer code, String data){
        this.code = code;
        this.data = data;
    }

    public String getName(){
        return this.data;
    }
}
