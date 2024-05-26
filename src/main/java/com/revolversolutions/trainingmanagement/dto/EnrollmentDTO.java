package com.revolversolutions.trainingmanagement.dto;


import com.revolversolutions.trainingmanagement.entity.EnrollmentId;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.enums.EnrolmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO {

    private EnrollmentId enrollmentId;
    private User user;
    private TrainingProgram program;
    private LocalDateTime enrolledOn;
    private LocalDateTime cancelledOn;
    private LocalDateTime completedOn;
    private LocalDateTime lastUpdateOn;
    private boolean completed;
    private String updatedBy;
    private EnrolmentStatus status;

}
