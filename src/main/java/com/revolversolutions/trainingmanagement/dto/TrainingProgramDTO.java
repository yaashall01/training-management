package com.revolversolutions.trainingmanagement.dto;

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
    private Long id;
    private String title;
    private String description;
    private int duration;
    private String content;
    private double fees;
    private String programType;
    private List<ReviewDTO> reviewS;
//    private List<SessionDTO> sessions;
}
