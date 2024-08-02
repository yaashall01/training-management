package com.revolversolutions.trainingmanagement.dto;

import com.revolversolutions.trainingmanagement.entity.FileDB;
import com.revolversolutions.trainingmanagement.enums.ProgramType;
import com.revolversolutions.trainingmanagement.enums.TrainingProgramLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingProgramDTO {

    private String programId;
    private String title;
    private String description;
    private int duration;
    private String content;
    private int enrollmentCount;
    private double fees;
    private String address;
    private TrainingProgramLevel level;
    private TrainingProgramLevel prerequisiteLevel;
    private String createdBy;
    private boolean isActive;
    private LocalDateTime createdAt;
    private ProgramType programType;
    private List<ReviewDTO> reviews;
    private List<FileDB> programImages;
    private List<SessionDTO> sessions;
}
