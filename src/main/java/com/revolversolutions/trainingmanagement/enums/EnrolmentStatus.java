package com.revolversolutions.trainingmanagement.enums;


public enum EnrolmentStatus {

    PENDING,
    ENROLLED,
    COMPLETED,
    CANCELLED;

    public static EnrolmentStatus fromString(String status) {
        for (EnrolmentStatus enrolmentStatus : EnrolmentStatus.values()) {
            if (enrolmentStatus.name().equalsIgnoreCase(status)) {
                return enrolmentStatus;
            }
        }
        return null;
    }
}
