package com.revolversolutions.trainingmanagement.enums;


public enum EnrolmentStatus {

    PENDING,
    PREREQUISITE_REVIEW,
    PAYMENT_REVIEW,
    ENROLLED,
    PARTICIPATION_PENDING,
    ATTENDANCE_VALIDATION,
    COMPLETED,
    CANCELLED,
    REJECTED;

    public static EnrolmentStatus fromString(String status) {
        for (EnrolmentStatus enrolmentStatus : EnrolmentStatus.values()) {
            if (enrolmentStatus.name().equalsIgnoreCase(status)) {
                return enrolmentStatus;
            }
        }
        return null;
    }
}
