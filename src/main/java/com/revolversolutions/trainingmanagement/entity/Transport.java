package com.revolversolutions.trainingmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "Transport")
@Table(name = "transport")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    @NaturalId
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String transportId;

    private String type;

    private String details;

    public Transport() {
        this.transportId = UUID.randomUUID().toString();
    }

    @PrePersist
    public void generateHotelId() {
        if (this.transportId == null) {
            this.transportId = UUID.randomUUID().toString();
        }
    }
}
