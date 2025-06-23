package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.dto.EnrollmentRequestDTO;
import com.revolversolutions.trainingmanagement.enums.EnrolmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    long getCountEnrollments();
    
    // New enhanced enrollment methods
    EnrollmentDTO createEnrollmentWithFiles(EnrollmentRequestDTO requestDTO) throws IOException;
    
    EnrollmentDTO approvePrerequisite(String enrollmentId, boolean approved, String adminNotes);
    
    EnrollmentDTO approvePayment(String enrollmentId, boolean approved, String adminNotes);
    
    Page<EnrollmentDTO> getEnrollmentsByStatus(EnrolmentStatus status, Pageable pageable);
    
    List<EnrollmentDTO> getPendingApprovals();
    
    byte[] downloadFile(String enrollmentId, String fileType) throws IOException;

}
