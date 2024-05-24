package com.revolversolutions.trainingmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private Long sessionId;
    private String title;
    private String startTime;
    private String endTime;
    private String location;
    private String description;
}
