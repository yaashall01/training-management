package com.revolversolutions.trainingmanagement.entity;


import com.revolversolutions.trainingmanagement.enums.EnrolmentStatus;
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

    private boolean completed;

    private String updatedBy;

    @Enumerated(EnumType.STRING)
    private EnrolmentStatus status = EnrolmentStatus.ENROLLED;

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



}
