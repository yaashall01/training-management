package com.revolversolutions.trainingmanagement.enums;


public enum UserGender {

    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String gender;

     UserGender(String gender){
        this.gender = gender;
     }

    public String getGender() {
        return gender;
    }
}
