package com.revolversolutions.trainingmanagement.dto;


import com.revolversolutions.trainingmanagement.enums.AttendanceStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttendanceDTO {
    private String userId;
    private String sessionId;
    private AttendanceStatus status;
    private String justification;
    private LocalDateTime attendedAt;
    private LocalDateTime updatedAt;
}
