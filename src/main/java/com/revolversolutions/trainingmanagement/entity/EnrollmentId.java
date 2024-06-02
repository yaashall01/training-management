package com.revolversolutions.trainingmanagement.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class EnrollmentId implements Serializable{

    @Column(name = "user_id")
    private Long userId;


    @Column(name = "program_id")
    private Long programId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(programId, that.programId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, programId);
    }
}
