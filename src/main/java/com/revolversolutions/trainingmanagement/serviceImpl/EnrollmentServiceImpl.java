package com.revolversolutions.trainingmanagement.serviceImpl;


import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.dto.EnrollmentRequestDTO;
import com.revolversolutions.trainingmanagement.entity.*;
import com.revolversolutions.trainingmanagement.enums.EnrolmentStatus;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.EnrollmentDTOMapper;
import com.revolversolutions.trainingmanagement.repository.EnrollmentRepository;
import com.revolversolutions.trainingmanagement.repository.FileDBRepository;
import com.revolversolutions.trainingmanagement.repository.TrainingProgramRepository;
import com.revolversolutions.trainingmanagement.repository.UserRepository;
import com.revolversolutions.trainingmanagement.service.EnrollmentService;
import com.revolversolutions.trainingmanagement.serviceImpl.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentDTOMapper enrollmentDTOMapper;
    private final UserRepository userRepository;
    private final TrainingProgramRepository trainingProgramRepository;
    private final FileStorageService fileStorageService;
    private final FileDBRepository fileDBRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, 
                                EnrollmentDTOMapper enrollmentDTOMapper, 
                                UserRepository userRepository, 
                                TrainingProgramRepository trainingProgramRepository,
                                FileStorageService fileStorageService,
                                FileDBRepository fileDBRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentDTOMapper = enrollmentDTOMapper;
        this.userRepository = userRepository;
        this.trainingProgramRepository = trainingProgramRepository;
        this.fileStorageService = fileStorageService;
        this.fileDBRepository = fileDBRepository;
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
    }    @Override
    public long getCountEnrollments() {
        return enrollmentRepository.count();
    }

    @Override
    @Transactional
    public EnrollmentDTO createEnrollmentWithFiles(EnrollmentRequestDTO requestDTO) throws IOException {
        log.info("Creating enrollment with files for user {} and program {}", 
                requestDTO.getUserId(), requestDTO.getProgramId());

        // Validate user and program
        User user = userRepository.findUserByUserId(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + requestDTO.getUserId()));

        TrainingProgram program = trainingProgramRepository.findByProgramId(requestDTO.getProgramId())
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + requestDTO.getProgramId()));

        // Check if user is already enrolled
        enrollmentRepository.findByEnrollmentId_UserIdAndEnrollmentId_ProgramId(user.getId(), program.getId())
                .ifPresent(enrollment -> {
                    throw new RuntimeException("User is already enrolled in this program");
                });

        // Update user info if provided
        updateUserInfoIfProvided(user, requestDTO);

        // Create enrollment entity
        EnrollmentId enrollmentId = new EnrollmentId(user.getId(), program.getId());
        Enrollment enrollment = Enrollment.builder()
                .id(enrollmentId)
                .user(user)
                .program(program)
                .paymentType(requestDTO.getPaymentType())
                .status(EnrolmentStatus.PENDING)
                .prerequisiteRequired(program.getPrerequisiteLevel() != null)
                .build();

        // Handle file uploads
        if (requestDTO.getPaymentProofFile() != null && !requestDTO.getPaymentProofFile().isEmpty()) {
            FileDB paymentProofFile = fileStorageService.store(requestDTO.getPaymentProofFile());
            enrollment.setPaymentProofFile(paymentProofFile);
        }

        if (requestDTO.getPrerequisiteProofFile() != null && !requestDTO.getPrerequisiteProofFile().isEmpty()) {
            FileDB prerequisiteProofFile = fileStorageService.store(requestDTO.getPrerequisiteProofFile());
            enrollment.setPrerequisiteProofFile(prerequisiteProofFile);
        }

        // Determine initial status based on requirements
        determineInitialStatus(enrollment);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        log.info("Enrollment created successfully with id: {}", savedEnrollment.getEnrollmentId());

        return enrollmentDTOMapper.toDto(savedEnrollment);
    }

    @Override
    @Transactional
    public EnrollmentDTO approvePrerequisite(String enrollmentId, boolean approved, String adminNotes) {
        log.info("Approving prerequisite for enrollment: {}, approved: {}", enrollmentId, approved);

        Enrollment enrollment = enrollmentRepository.findEnrollmentByEnrollmentId(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + enrollmentId));

        if (approved) {
            enrollment.approvePrerequisite(adminNotes);
        } else {
            enrollment.reject("Prerequisite not approved: " + adminNotes);
        }

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return enrollmentDTOMapper.toDto(savedEnrollment);
    }

    @Override
    @Transactional
    public EnrollmentDTO approvePayment(String enrollmentId, boolean approved, String adminNotes) {
        log.info("Approving payment for enrollment: {}, approved: {}", enrollmentId, approved);

        Enrollment enrollment = enrollmentRepository.findEnrollmentByEnrollmentId(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + enrollmentId));

        if (approved) {
            enrollment.approvePayment(adminNotes);
        } else {
            enrollment.reject("Payment not approved: " + adminNotes);
        }

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return enrollmentDTOMapper.toDto(savedEnrollment);
    }

    @Override
    public Page<EnrollmentDTO> getEnrollmentsByStatus(EnrolmentStatus status, Pageable pageable) {
        log.info("Fetching enrollments with status: {}", status);
        Page<Enrollment> enrollments = enrollmentRepository.findByStatus(status, pageable);
        return enrollments.map(enrollmentDTOMapper::toDto);
    }

    @Override
    public List<EnrollmentDTO> getPendingApprovals() {
        log.info("Fetching enrollments pending approval");
        List<Enrollment> pendingEnrollments = enrollmentRepository.findByStatusIn(
                List.of(EnrolmentStatus.PENDING, EnrolmentStatus.PREREQUISITE_REVIEW, EnrolmentStatus.PAYMENT_REVIEW)
        );
        return pendingEnrollments.stream()
                .map(enrollmentDTOMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public byte[] downloadFile(String enrollmentId, String fileType) throws IOException {
        log.info("Downloading file for enrollment: {}, type: {}", enrollmentId, fileType);

        Enrollment enrollment = enrollmentRepository.findEnrollmentByEnrollmentId(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + enrollmentId));

        FileDB file = null;
        if ("payment".equalsIgnoreCase(fileType)) {
            file = enrollment.getPaymentProofFile();
        } else if ("prerequisite".equalsIgnoreCase(fileType)) {
            file = enrollment.getPrerequisiteProofFile();
        }

        if (file == null) {
            throw new ResourceNotFoundException("File not found for type: " + fileType);
        }

        return file.getData();
    }

    private void updateUserInfoIfProvided(User user, EnrollmentRequestDTO requestDTO) {
        if (requestDTO.getFirstName() != null) user.setFirstName(requestDTO.getFirstName());
        if (requestDTO.getLastName() != null) user.setLastName(requestDTO.getLastName());
        if (requestDTO.getPhone() != null) user.setPhone(requestDTO.getPhone());
        
        // Update address if any address field is provided
        if (requestDTO.getCity() != null || requestDTO.getCountry() != null || 
            requestDTO.getState() != null || requestDTO.getStreet() != null || 
            requestDTO.getZipCode() != null) {
            
            Address address = user.getAddress() != null ? user.getAddress() : new Address();
            if (requestDTO.getCity() != null) address.setCity(requestDTO.getCity());
            if (requestDTO.getCountry() != null) address.setCountry(requestDTO.getCountry());
            if (requestDTO.getState() != null) address.setState(requestDTO.getState());
            if (requestDTO.getStreet() != null) address.setStreet(requestDTO.getStreet());
            if (requestDTO.getZipCode() != null) address.setZipCode(requestDTO.getZipCode());
            
            user.setAddress(address);
        }
        
        userRepository.save(user);
    }

    private void determineInitialStatus(Enrollment enrollment) {
        // If prerequisite is required and not provided, set to PREREQUISITE_REVIEW
        if (enrollment.getPrerequisiteRequired() && enrollment.getPrerequisiteProofFile() == null) {
            enrollment.setStatus(EnrolmentStatus.PREREQUISITE_REVIEW);
            return;
        }
        
        // If payment type is VERMENT and no proof provided, set to PAYMENT_REVIEW
        if (enrollment.getPaymentType() == com.revolversolutions.trainingmanagement.enums.PaymentType.VERMENT && 
            enrollment.getPaymentProofFile() == null) {
            enrollment.setStatus(EnrolmentStatus.PAYMENT_REVIEW);
            return;
        }
        
        // If all required documents are provided, set to appropriate review status
        if (enrollment.getPrerequisiteRequired() && enrollment.getPrerequisiteProofFile() != null) {
            enrollment.setStatus(EnrolmentStatus.PREREQUISITE_REVIEW);
        } else if (enrollment.getPaymentType() == com.revolversolutions.trainingmanagement.enums.PaymentType.VERMENT && 
                   enrollment.getPaymentProofFile() != null) {
            enrollment.setStatus(EnrolmentStatus.PAYMENT_REVIEW);
        } else {
            // Cash payment or online payment - can be enrolled directly
            enrollment.setStatus(EnrolmentStatus.ENROLLED);
        }
    }


}
