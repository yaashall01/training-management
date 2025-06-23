package com.revolversolutions.trainingmanagement.entity;


import com.revolversolutions.trainingmanagement.enums.EnrolmentStatus;
import com.revolversolutions.trainingmanagement.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity(name = "Enrollment")
@Table(name = "enrollment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id;

    @Column(unique = true, nullable = false, updatable = false)
    @NaturalId
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String enrollmentId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(
                    name = "enrollment_user_id_fk"
            )
    )
    private User user;

    @ManyToOne
    @MapsId("programId")
    @JoinColumn(
            name = "program_id",
            foreignKey = @ForeignKey(
                    name = "enrollment_training_program_id_fk"
            )
    )
    private TrainingProgram program;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime enrolledOn;

    private LocalDateTime cancelledOn;

    private LocalDateTime completedOn;

    @UpdateTimestamp
    private LocalDateTime lastUpdateOn;

    private boolean completed;    private String updatedBy;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EnrolmentStatus status = EnrolmentStatus.PENDING;

    // New fields for enhanced enrollment process
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PaymentType paymentType = PaymentType.CASH;

    @OneToOne
    @JoinColumn(name = "payment_proof_file_id")
    private FileDB paymentProofFile;

    @OneToOne
    @JoinColumn(name = "prerequisite_proof_file_id")
    private FileDB prerequisiteProofFile;

    @Builder.Default
    private Boolean prerequisiteRequired = false;

    @Builder.Default
    private Boolean prerequisiteApproved = false;

    @Builder.Default
    private Boolean paymentApproved = false;

    @Column(columnDefinition = "TEXT")
    private String adminNotes;

    private LocalDateTime prerequisiteApprovedAt;

    private LocalDateTime paymentApprovedAt;

    @PrePersist
    protected void onCreate() {
        if (enrollmentId == null) {
            enrollmentId = UUID.randomUUID().toString();
        }
    }
    public void completedOn(){
        this.completedOn = LocalDateTime.now();
        this.status = EnrolmentStatus.COMPLETED;
        this.completed = true;
    }

    public void cancel(){
        this.status = EnrolmentStatus.CANCELLED;
        this.cancelledOn = LocalDateTime.now();
    }

    public void approvePrerequisite(String adminNotes) {
        this.prerequisiteApproved = true;
        this.prerequisiteApprovedAt = LocalDateTime.now();
        this.adminNotes = adminNotes;
        updateStatusAfterApproval();
    }

    public void approvePayment(String adminNotes) {
        this.paymentApproved = true;
        this.paymentApprovedAt = LocalDateTime.now();
        this.adminNotes = adminNotes;
        updateStatusAfterApproval();
    }

    public void reject(String reason) {
        this.status = EnrolmentStatus.REJECTED;
        this.adminNotes = reason;
        this.lastUpdateOn = LocalDateTime.now();
    }

    private void updateStatusAfterApproval() {
        // If prerequisite is required and not approved, status should be PREREQUISITE_REVIEW
        if (prerequisiteRequired && !prerequisiteApproved) {
            this.status = EnrolmentStatus.PREREQUISITE_REVIEW;
            return;
        }
        
        // If payment type is VERMENT and not approved, status should be PAYMENT_REVIEW
        if (paymentType == PaymentType.VERMENT && !paymentApproved) {
            this.status = EnrolmentStatus.PAYMENT_REVIEW;
            return;
        }
        
        // If all required approvals are done, status should be ENROLLED
        if ((!prerequisiteRequired || prerequisiteApproved) && 
            (paymentType == PaymentType.CASH || paymentType == PaymentType.ONLINE || paymentApproved)) {
            this.status = EnrolmentStatus.ENROLLED;
        }
    }

    public boolean isReadyForEnrollment() {
        return (!prerequisiteRequired || prerequisiteApproved) && 
               (paymentType == PaymentType.CASH || paymentType == PaymentType.ONLINE || paymentApproved);
    }



}
