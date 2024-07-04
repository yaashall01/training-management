package com.revolversolutions.trainingmanagement.entity;

import com.revolversolutions.trainingmanagement.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "Session")
@Table(name = "session")
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

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    private String location;

    @Lob
    private String description;

    @OneToMany(
            mappedBy = "session",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Attendance> users = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString();
        }
    }
}
