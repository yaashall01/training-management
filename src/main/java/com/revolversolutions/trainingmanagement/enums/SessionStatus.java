package com.revolversolutions.trainingmanagement.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SessionStatus {

    COMPLETED("completed"),
    IN_PROGRESS("in progress"),
    NOT_STARTED("not started");

    private String sessionStatus;

    SessionStatus(String sessionStatus){
        this.sessionStatus = sessionStatus;
    }

    @JsonValue
    public String getSessionStatus() {
        return sessionStatus;
    }

    @JsonCreator
    public static SessionStatus fromValue(String value){
        for (SessionStatus status : values()) {
            String currentStatus = status.getSessionStatus();
            if (currentStatus.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value for Contact type Enum: " + value);
    }
}