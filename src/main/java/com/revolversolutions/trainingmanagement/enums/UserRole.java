package com.revolversolutions.trainingmanagement.enums;


import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("Admin"),
    TRAINER("Trainer"),
    TRAINEE("Trainee");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

}
