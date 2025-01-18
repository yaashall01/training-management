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
@Entity(name = "PickupPoint")
@Table(name = "pickup_point")
public class PickupPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String pickupPointId;
    private String name;
    private String location;
    private String locationUrl;
    @ManyToOne
    @JoinColumn(name = "logistics_id", insertable = false, updatable = false)
    private Logistic logistics;

}
