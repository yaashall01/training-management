package com.revolversolutions.trainingmanagement.entity;


import com.revolversolutions.trainingmanagement.enums.ActionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private LocalDateTime timestamp;
    private String fullName;
    private String ipAddress;
    @Enumerated(EnumType.STRING)
    private ActionType actionType;
}