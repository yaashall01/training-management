package com.revolversolutions.trainingmanagement.enums;


import lombok.Getter;

@Getter
public enum UserRole {

    ROLE_ADMIN( "Admin"),
    ROLE_TRAINER("Trainer"),
    ROLE_TRAINEE("Trainee");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    @Override
    public String toString() {
        return this.role;
    }
}
