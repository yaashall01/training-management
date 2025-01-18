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
@Entity(name = "Logistic")
@Table(name = "logistics")
public class Logistic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String logisticsId;

    private String name;

    @OneToMany
    @JoinColumn(name = "logistics_id", insertable = false, updatable = false)
    private List<Hotel> hotels = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "logistics_id")
    private List<PickupPoint> pickupPoints = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "logistics_id")
    private List<Transport> transports = new ArrayList<>();


}
