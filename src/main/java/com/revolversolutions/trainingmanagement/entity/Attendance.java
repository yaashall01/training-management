package com.revolversolutions.trainingmanagement.entity;


import com.revolversolutions.trainingmanagement.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity(name = "Attendance")
@Table(name = "attendance")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Attendance {

    @EmbeddedId
    private AttendanceId id;

    @Column(unique = true, nullable = false, updatable = false)
    @NaturalId
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String attendanceId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(
                    name = "attendance_user_id_fk"
            )
    )
    private User user;

    @ManyToOne
    @MapsId("sessionId")
    @JoinColumn(
            name = "session_id",
            foreignKey = @ForeignKey(
                    name = "attendance_session_id_fk"
            )
    )
    private Session session;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    @Lob
    private String justification;

    @CreationTimestamp
    private LocalDateTime attendedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (attendanceId == null) {
            attendanceId = UUID.randomUUID().toString();
        }
    }

}
