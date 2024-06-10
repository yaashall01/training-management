package com.revolversolutions.trainingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    @NaturalId
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private TrainingProgram program;

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String location;

    @Lob
    private String description;


    @PrePersist
    protected void onCreate() {
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString();
        }
    }
}
