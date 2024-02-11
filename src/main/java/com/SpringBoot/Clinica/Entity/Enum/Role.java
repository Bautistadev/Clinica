package com.SpringBoot.Clinica.Entity.Enum;

public enum Role {

    ADMIN("ADMIN"),USER("USER");

    private String roleName;

    private Role(String role){
        this.roleName = role;
    }

    public String getRoleName(){
        return this.roleName.toString();
    }
}
