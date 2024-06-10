package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.ResponseTrainingProgramPage;
import com.revolversolutions.trainingmanagement.dto.TrainingProgramDTO;
import com.revolversolutions.trainingmanagement.entity.FileDB;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TrainingProgramService {
    TrainingProgramDTO createTrainingProgram(TrainingProgramDTO programDTO);

    TrainingProgramDTO getTrainingProgramById(String programId);
    TrainingProgram getProgramEntityById(String programId);

    ResponseTrainingProgramPage getAllTrainingPrograms(int pageNo, int pageSize, String sortBy, String sortDir , String search , String programType);

    TrainingProgramDTO updateTrainingProgram(String programId ,TrainingProgramDTO programDTO);

    void deleteTrainingProgram(String programId);
    void deleteTrainingProgramsByIds(List<Long> ids);

    List<FileDB> getProgramImages(String programId);

    void uploadProgramImages(String userId, List<MultipartFile> files) throws IOException;

    void uploadProgramImage(String userId, MultipartFile file) throws IOException;
}
