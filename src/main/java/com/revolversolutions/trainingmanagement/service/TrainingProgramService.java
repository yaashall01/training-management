package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.ResponseTrainingProgramPage;
import com.revolversolutions.trainingmanagement.dto.TrainingProgramDTO;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import com.revolversolutions.trainingmanagement.enums.ProgramType;

import java.util.List;

public interface TrainingProgramService {
    TrainingProgramDTO createTrainingProgram(TrainingProgramDTO programDTO);

    TrainingProgramDTO getTrainingProgramById(Long id);
    TrainingProgram getProgramEntityById(Long id);

    ResponseTrainingProgramPage getAllTrainingPrograms(int pageNo, int pageSize, String sortBy, String sortDir , String search , String programType);

    TrainingProgramDTO updateTrainingProgram(Long id ,TrainingProgramDTO programDTO);

    void deleteTrainingProgram(Long id);
    void deleteTrainingProgramsByIds(List<Long> ids);

}
