package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.aspect.UserActivityLog;
import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.enums.ActionType;
import com.revolversolutions.trainingmanagement.enums.EnrolmentStatus;
import com.revolversolutions.trainingmanagement.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/enrollment-approval")
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class EnrollmentApprovalController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentApprovalController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/pending")
    public ResponseEntity<Page<EnrollmentDTO>> getPendingEnrollments(Pageable pageable) {
        Page<EnrollmentDTO> pendingEnrollments = enrollmentService.getEnrollmentsByStatus(
                EnrolmentStatus.PENDING, pageable);
        return ResponseEntity.ok(pendingEnrollments);
    }

    @GetMapping("/prerequisite-review")
    public ResponseEntity<Page<EnrollmentDTO>> getPrerequisiteReviewEnrollments(Pageable pageable) {
        Page<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByStatus(
                EnrolmentStatus.PREREQUISITE_REVIEW, pageable);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/payment-review")
    public ResponseEntity<Page<EnrollmentDTO>> getPaymentReviewEnrollments(Pageable pageable) {
        Page<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByStatus(
                EnrolmentStatus.PAYMENT_REVIEW, pageable);
        return ResponseEntity.ok(enrollments);
    }    @PostMapping("/{enrollmentId}/approve-prerequisite")
    @UserActivityLog(action = "Admin Approve Prerequisite", actionType = ActionType.UPDATE)
    public ResponseEntity<EnrollmentDTO> approvePrerequisite(
            @PathVariable String enrollmentId,
            @RequestParam(value = "notes", required = false) String notes) {
        
        EnrollmentDTO enrollment = enrollmentService.approvePrerequisite(enrollmentId, true, notes);
        return ResponseEntity.ok(enrollment);
    }

    @PostMapping("/{enrollmentId}/reject-prerequisite")
    @UserActivityLog(action = "Admin Reject Prerequisite", actionType = ActionType.UPDATE)
    public ResponseEntity<EnrollmentDTO> rejectPrerequisite(
            @PathVariable String enrollmentId,
            @RequestParam(value = "reason", required = false) String reason) {
        
        EnrollmentDTO enrollment = enrollmentService.approvePrerequisite(enrollmentId, false, reason);
        return ResponseEntity.ok(enrollment);
    }

    @PostMapping("/{enrollmentId}/approve-payment")
    @UserActivityLog(action = "Admin Approve Payment", actionType = ActionType.UPDATE)
    public ResponseEntity<EnrollmentDTO> approvePayment(
            @PathVariable String enrollmentId,
            @RequestParam(value = "notes", required = false) String notes) {
        
        EnrollmentDTO enrollment = enrollmentService.approvePayment(enrollmentId, true, notes);
        return ResponseEntity.ok(enrollment);
    }

    @PostMapping("/{enrollmentId}/reject-payment")
    @UserActivityLog(action = "Admin Reject Payment", actionType = ActionType.UPDATE)
    public ResponseEntity<EnrollmentDTO> rejectPayment(
            @PathVariable String enrollmentId,
            @RequestParam(value = "reason", required = false) String reason) {
        
        EnrollmentDTO enrollment = enrollmentService.approvePayment(enrollmentId, false, reason);
        return ResponseEntity.ok(enrollment);
    }    @GetMapping("/files/{enrollmentId}/payment-proof")
    public ResponseEntity<byte[]> getPaymentProofFile(@PathVariable String enrollmentId) {
        try {
            byte[] fileData = enrollmentService.downloadFile(enrollmentId, "payment");
            return ResponseEntity.ok()
                    .header("Content-Type", "application/octet-stream")
                    .body(fileData);
        } catch (Exception e) {
            log.error("Error retrieving payment proof file", e);
            return ResponseEntity.notFound().build();
        }
    }    @GetMapping("/files/{enrollmentId}/prerequisite-proof")
    public ResponseEntity<byte[]> getPrerequisiteProofFile(@PathVariable String enrollmentId) {
        try {
            byte[] fileData = enrollmentService.downloadFile(enrollmentId, "prerequisite");
            return ResponseEntity.ok()
                    .header("Content-Type", "application/octet-stream")
                    .body(fileData);
        } catch (Exception e) {
            log.error("Error retrieving prerequisite proof file", e);
            return ResponseEntity.notFound().build();
        }
    }
}
