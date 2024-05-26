package com.revolversolutions.trainingmanagement.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentId implements Serializable{

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "program_id")
    private Long programId;

}
