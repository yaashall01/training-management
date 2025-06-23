package com.revolversolutions.trainingmanagement.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ApprovalType {
    
    PREREQUISITE("prerequisite"),
    PAYMENT("payment"),
    PARTICIPATION("participation"),
    ATTENDANCE("attendance");
    
    private final String value;
    
    ApprovalType(String value) {
        this.value = value;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    @JsonCreator
    public static ApprovalType fromValue(String value) {
        for (ApprovalType approvalType : values()) {
            if (approvalType.getValue().equalsIgnoreCase(value)) {
                return approvalType;
            }
        }
        throw new IllegalArgumentException("Invalid approval type: " + value);
    }
    
    @Override
    public String toString() {
        return this.value;
    }
}
