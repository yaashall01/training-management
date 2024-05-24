package com.revolversolutions.trainingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private TrainingProgram program;

    private String tiltle;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String location;

    @Lob
    private String description;
}
