package com.revolversolutions.trainingmanagement.serviceImpl;


import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.entity.Enrollment;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.EnrollmentDTOMapper;
import com.revolversolutions.trainingmanagement.repository.EnrollmentRepository;
import com.revolversolutions.trainingmanagement.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentDTOMapper enrollmentDTOMapper;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, EnrollmentDTOMapper enrollmentDTOMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentDTOMapper = enrollmentDTOMapper;
    }


    @Override
    public EnrollmentDTO updateEnrollment(Long userId, Long programId, EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = enrollmentRepository.findByEnrollmentId_UserIdAndEnrollmentId_ProgramId(userId, programId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));

        enrollment.setCancelledOn(enrollmentDTO.getCancelledOn());
        enrollment.setCompletedOn(enrollmentDTO.getCompletedOn());
        enrollment.setCompleted(enrollmentDTO.isCompleted());
        enrollment.setUpdatedBy(enrollmentDTO.getUpdatedBy());
        enrollment.setStatus(enrollmentDTO.getStatus());

        return enrollmentDTOMapper.toDto(enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentDTO getEnrollment(Long userId, Long programId) {
        return enrollmentRepository.findByEnrollmentId_UserIdAndEnrollmentId_ProgramId(userId, programId)
                .map(enrollmentDTOMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
    }

    @Override
    public void deleteEnrollment(Long userId, Long programId) {
        Enrollment enrollment = enrollmentRepository.findByEnrollmentId_UserIdAndEnrollmentId_ProgramId(userId, programId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        enrollmentRepository.delete(enrollment);
    }


}
