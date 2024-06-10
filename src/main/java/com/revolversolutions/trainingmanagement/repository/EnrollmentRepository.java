package com.revolversolutions.trainingmanagement.repository;


import com.revolversolutions.trainingmanagement.entity.Enrollment;
import com.revolversolutions.trainingmanagement.entity.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("SELECT e FROM Enrollment e WHERE e.id.userId = ?1 AND e.id.programId = ?2")
    Optional<Enrollment> findByEnrollmentId_UserIdAndEnrollmentId_ProgramId(Long userId, Long programId);

    boolean existsById(EnrollmentId enrollmentId);
}
