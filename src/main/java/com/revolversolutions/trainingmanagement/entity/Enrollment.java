package com.revolversolutions.trainingmanagement.entity;


import com.revolversolutions.trainingmanagement.enums.EnrolmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


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
    private EnrollmentId enrollmentId;

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
