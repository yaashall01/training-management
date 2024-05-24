package com.revolversolutions.trainingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private TrainingProgram program;

    private double rating;
    private String content;
    @CreationTimestamp
    private LocalDateTime creationDate;
}
