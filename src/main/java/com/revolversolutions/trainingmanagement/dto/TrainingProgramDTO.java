package com.revolversolutions.trainingmanagement.dto;

import com.revolversolutions.trainingmanagement.entity.FileDB;
import com.revolversolutions.trainingmanagement.enums.ProgramType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private double fees;
    private ProgramType programType;
    private List<ReviewDTO> reviews;
    private List<FileDB> programImages;
    private List<SessionDTO> sessions;
}
