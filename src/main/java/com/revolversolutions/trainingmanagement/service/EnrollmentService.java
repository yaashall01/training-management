package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;

/**
 * @author Yassine CHALH
 */
public interface EnrollmentService {
    EnrollmentDTO updateEnrollment(Long userId, Long programId, EnrollmentDTO enrollmentDTO);

    EnrollmentDTO getEnrollment(Long userId, Long programId);

    void deleteEnrollment(Long userId, Long programId);
}
