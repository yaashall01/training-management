package com.revolversolutions.trainingmanagement.entity;

import com.revolversolutions.trainingmanagement.aspect.TrainingProgramListener;
import com.revolversolutions.trainingmanagement.enums.ProgramType;
import com.revolversolutions.trainingmanagement.enums.TrainingProgramLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "TrainingProgram")
@Table(name = "program")
@EntityListeners(TrainingProgramListener.class)
public class TrainingProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    @NaturalId
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String programId;

    private String title;

    private String description;

    private int duration;

    @Lob
    private String content;

    private String address;

    private double fees;

    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private ProgramType programType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingProgramLevel level;

    @Enumerated(EnumType.STRING)
    @Column(name = "prerequisite_level")
    private TrainingProgramLevel prerequisiteLevel;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @OneToMany(
            mappedBy = "program",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Review> reviews;

    @OneToMany(
            mappedBy = "program",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Session> sessions;

    @OneToMany(
            mappedBy = "program",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany
    private List<FileDB> programImages = new ArrayList<>();

    public TrainingProgram(){
        this.programId = UUID.randomUUID().toString();
    }

    @PrePersist
    protected void onCreate() {
        if (programId == null) {
            programId = UUID.randomUUID().toString();
        }
    }

    public void addEnrolment(Enrollment enrollment) {
        if (!enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
        }
    }
    public void removeEnrolment(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }

}
