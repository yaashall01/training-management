package com.revolversolutions.trainingmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "Cta")
@Table(name = "cta")
public class Cta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ctaId;

    private String ctaTitle;
    private String ctaSub;
    private String buttonText;
    private String leadingTo;

    @OneToOne( cascade = CascadeType.REMOVE ,orphanRemoval = true)
    private ImageMetadata image;


    @ManyToOne
    @JoinColumn(name = "landing_page_id")
    private LandingPage landingPage;

}
