package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TrainingProgramRepository extends JpaRepository<TrainingProgram , Long > , JpaSpecificationExecutor<TrainingProgram> {
    void deleteByProgramIdIn(List<Long> ids);
}
