package com.revolversolutions.trainingmanagement.serviceImpl;


import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.dto.UserResponse;
import com.revolversolutions.trainingmanagement.entity.Enrollment;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.EnrollmentDTOMapper;
import com.revolversolutions.trainingmanagement.repository.EnrollmentRepository;
import com.revolversolutions.trainingmanagement.repository.TrainingProgramRepository;
import com.revolversolutions.trainingmanagement.repository.UserRepository;
import com.revolversolutions.trainingmanagement.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentDTOMapper enrollmentDTOMapper;
    private final UserRepository userRepository;
    private final TrainingProgramRepository trainingProgramRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, EnrollmentDTOMapper enrollmentDTOMapper, UserRepository userRepository, TrainingProgramRepository trainingProgramRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentDTOMapper = enrollmentDTOMapper;
        this.userRepository = userRepository;
        this.trainingProgramRepository = trainingProgramRepository;
    }


    @Override
    public EnrollmentDTO updateEnrollment(String userId, String programId, EnrollmentDTO enrollmentDTO) {

        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TrainingProgram program = trainingProgramRepository.findByProgramId(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));


        Enrollment enrollment = enrollmentRepository.findByEnrollmentId_UserIdAndEnrollmentId_ProgramId(user.getId(), program.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with"));

        enrollment.setCancelledOn(enrollmentDTO.getCancelledOn());
        enrollment.setCompletedOn(enrollmentDTO.getCompletedOn());
        enrollment.setCompleted(enrollmentDTO.isCompleted());
        enrollment.setUpdatedBy(enrollmentDTO.getUpdatedBy());
        enrollment.setStatus(enrollmentDTO.getStatus());

        return enrollmentDTOMapper.toDto(enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentDTO getEnrollment(String userId, String programId) {
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TrainingProgram program = trainingProgramRepository.findByProgramId(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));

        return enrollmentRepository.findByEnrollmentId_UserIdAndEnrollmentId_ProgramId(user.getId(), program.getId())
                .map(enrollmentDTOMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
    }

    @Override
    public Page<EnrollmentDTO> getAllEnrollment(Pageable pageable){
        log.info("Fetching all enrollments");
        Page<Enrollment> enrollments = enrollmentRepository.findAll(pageable);
        log.info("Fetched all enrollments");
        return enrollments.map(enrollmentDTOMapper::toDto);
    }

    @Override
    public void deleteEnrollment(String userId, String programId) {
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TrainingProgram program = trainingProgramRepository.findByProgramId(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));

        Enrollment enrollment = enrollmentRepository.findByEnrollmentId_UserIdAndEnrollmentId_ProgramId(user.getId(), program.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        enrollmentRepository.delete(enrollment);
    }

    @Override
    public EnrollmentDTO getEnrollmentByEnrollmentId(String enrollmentId) {
        return enrollmentRepository.findEnrollmentByEnrollmentId(enrollmentId)
                .map(enrollmentDTOMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
    }


}
