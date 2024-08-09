package com.revolversolutions.trainingmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.naming.factory.SendMailFactory;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "Hotel")
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String hotelId;

    private String name;

    private double priceSingle;

    private double priceDouble;

    private String address;

    private String website;

    private String email;

    private String phone;

    public Hotel() {
        this.hotelId = UUID.randomUUID().toString();
    }

    @PrePersist
    public void generateHotelId() {
        if (this.hotelId == null) {
            this.hotelId = UUID.randomUUID().toString();
        }
    }
}
