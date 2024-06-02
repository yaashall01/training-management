package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TrainingProgramRepository extends JpaRepository<TrainingProgram , Long > , JpaSpecificationExecutor<TrainingProgram> {
    void deleteByProgramIdIn(List<Long> ids);
    void deleteByProgramId(String programId);

    Optional<TrainingProgram> findByProgramId(String programId);
}
