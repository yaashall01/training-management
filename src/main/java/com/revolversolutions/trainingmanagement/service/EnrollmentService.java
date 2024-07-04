package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Yassine CHALH
 */
public interface EnrollmentService {
    EnrollmentDTO updateEnrollment(String userId, String programId, EnrollmentDTO enrollmentDTO);

    EnrollmentDTO getEnrollment(String userId, String programId);

    Page<EnrollmentDTO> getAllEnrollment(Pageable pageable);

    void deleteEnrollment(String userId, String programId);

    EnrollmentDTO getEnrollmentByEnrollmentId(String enrollmentId);

}
