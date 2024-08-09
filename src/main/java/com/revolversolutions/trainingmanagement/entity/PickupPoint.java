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
@Entity(name = "PickupPoint")
@Table(name = "pickup_point")
public class PickupPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    @NaturalId
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String pickupPointId;

    private String location;

    //Date de depart a Yassen

    public PickupPoint() {
        this.pickupPointId = UUID.randomUUID().toString();
    }

    @PrePersist
    public void generateHotelId() {
        if (this.pickupPointId == null) {
            this.pickupPointId = UUID.randomUUID().toString();
        }
    }
}
