package com.revolversolutions.trainingmanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "Logistic")
@Table(name = "logistics")
public class Logistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    @NaturalId
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String logisticsId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "logistics_id")
    private List<Hotel> hotels = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "logistics_id")
    private List<PickupPoint> pickupPoints = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "logistics_id")
    private List<Transport> transports = new ArrayList<>();

    public Logistic() {
        this.logisticsId = UUID.randomUUID().toString();
    }


    @PrePersist
    protected void onCreate() {
        if (logisticsId == null) {
            logisticsId = UUID.randomUUID().toString();
        }
    }

}
