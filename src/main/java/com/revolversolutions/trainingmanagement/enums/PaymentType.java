package com.revolversolutions.trainingmanagement.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum PaymentType {
    
    CASH("cash"),
    VERMENT("verment"),
    ONLINE("online");
    
    private final String value;
    
    PaymentType(String value) {
        this.value = value;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    @JsonCreator
    public static PaymentType fromValue(String value) {
        for (PaymentType paymentType : values()) {
            if (paymentType.getValue().equalsIgnoreCase(value)) {
                return paymentType;
            }
        }
        throw new IllegalArgumentException("Invalid payment type: " + value);
    }
    
    @Override
    public String toString() {
        return this.value;
    }
}
