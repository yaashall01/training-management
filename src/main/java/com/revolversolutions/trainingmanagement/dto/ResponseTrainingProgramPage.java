package com.revolversolutions.trainingmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ResponseTrainingProgramPage {
    private List<TrainingProgramDTO> items;
    private int pageIndex;
    private int pageSize;
    private long totalElements;
    private int pageCount;
    private boolean last;
}
