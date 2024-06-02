package com.revolversolutions.trainingmanagement.controller;


import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/enrollment")
public class EnrollementController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollementController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<Page<EnrollmentDTO>> getAllEnrollments(Pageable pageable){
        return ResponseEntity.ok(enrollmentService.getAllEnrollment(pageable));
    }

    @PutMapping("/{userId}/{programId}")
    public ResponseEntity<EnrollmentDTO> updateEnrollment(
            @PathVariable String userId,
            @PathVariable String programId,
            @RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO updatedEnrollment = enrollmentService.updateEnrollment(userId, programId, enrollmentDTO);
        return ResponseEntity.ok(updatedEnrollment);
    }

    @GetMapping("/{userId}/{programId}")
    public ResponseEntity<EnrollmentDTO> getEnrollment(
            @PathVariable String userId,
            @PathVariable String programId) {
        EnrollmentDTO enrollment = enrollmentService.getEnrollment(userId, programId);
        return ResponseEntity.ok(enrollment);
    }

    @DeleteMapping("/{userId}/{programId}")
    public ResponseEntity<Void> deleteEnrollment(
            @PathVariable String userId,
            @PathVariable String programId) {
        enrollmentService.deleteEnrollment(userId, programId);
        return ResponseEntity.noContent().build();
    }
}
