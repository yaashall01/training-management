package com.revolversolutions.trainingmanagement.dto;


import com.revolversolutions.trainingmanagement.dto.user.UserResponse;
import com.revolversolutions.trainingmanagement.enums.EnrolmentStatus;
import com.revolversolutions.trainingmanagement.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {    private String enrollmentId;
    private UserResponse user;
    private TrainingProgramDTO program;
    private LocalDateTime enrolledOn;
    private LocalDateTime cancelledOn;
    private LocalDateTime completedOn;
    private LocalDateTime lastUpdateOn;
    private boolean completed;
    private String updatedBy;
    private EnrolmentStatus status;
    
    // New fields for enhanced enrollment process
    private PaymentType paymentType;
    private String paymentProofFileId;
    private String paymentProofFileName;
    private String prerequisiteProofFileId;
    private String prerequisiteProofFileName;
    private Boolean prerequisiteRequired;
    private Boolean prerequisiteApproved;
    private Boolean paymentApproved;
    private String adminNotes;
    private LocalDateTime prerequisiteApprovedAt;
    private LocalDateTime paymentApprovedAt;

}
