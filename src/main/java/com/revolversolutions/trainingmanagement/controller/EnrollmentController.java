package com.revolversolutions.trainingmanagement.controller;


import com.revolversolutions.trainingmanagement.aspect.UserActivityLog;
import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.entity.Enrollment;
import com.revolversolutions.trainingmanagement.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<EnrollmentDTO>> getAllEnrollments(Pageable pageable){
        return ResponseEntity.ok(enrollmentService.getAllEnrollment(pageable));
    }

    @PutMapping("/{userId}/{programId}")
    @UserActivityLog(action = "Admin Delete Enrollment")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnrollmentDTO> updateEnrollment(
            @PathVariable String userId,
            @PathVariable String programId,
            @RequestBody EnrollmentDTO enrollmentDTO) {
        EnrollmentDTO updatedEnrollment = enrollmentService.updateEnrollment(userId, programId, enrollmentDTO);
        return ResponseEntity.ok(updatedEnrollment);
    }

    @GetMapping("/{userId}/{programId}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentByEmbeddableId(
            @PathVariable String userId,
            @PathVariable String programId) {
        EnrollmentDTO enrollment = enrollmentService.getEnrollment(userId, programId);
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping("id/{enrollmentId}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentByEnrollmentId(@PathVariable String enrollmentId){
        EnrollmentDTO enrollment = enrollmentService.getEnrollmentByEnrollmentId(enrollmentId);
        return ResponseEntity.ok(enrollment);
    }


    @DeleteMapping("/{userId}/{programId}")
    @PreAuthorize("hasRole('ADMIN')")
    @UserActivityLog(action = "Admin Delete Enrollment")
    public ResponseEntity<Void> deleteEnrollment(
            @PathVariable String userId,
            @PathVariable String programId) {
        enrollmentService.deleteEnrollment(userId, programId);
        return ResponseEntity.noContent().build();
    }
}
