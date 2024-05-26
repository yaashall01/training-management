package com.revolversolutions.trainingmanagement.entity;

import com.revolversolutions.trainingmanagement.enums.ProgramType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "TrainingProgram")
@Table(name = "program")
public class TrainingProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;

    private String title;
    private String description;
    private int duration;
    @Lob
    private String content;
    private String address;
    private double fees;
    @Enumerated(EnumType.STRING)
    private ProgramType programType;
    @ManyToOne
    private TrainingProgram prerequisite;
    private int position;
    @CreationTimestamp
    private LocalDateTime createdAt;
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


    public void addEnrolment(Enrollment enrollment) {
        if (!enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
        }
    }
    public void removeEnrolment(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }

}
