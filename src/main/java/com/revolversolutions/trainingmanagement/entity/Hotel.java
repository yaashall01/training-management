package com.revolversolutions.trainingmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.naming.factory.SendMailFactory;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "Hotel")
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String hotelId;

    private String name;

    private double priceSingle;

    private double priceDouble;

    @Embedded
    private Address address;

    private String website;

    private String email;

    private String phone;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "logistics_id", insertable = false, updatable = false)
    private Logistic logistics;



}
