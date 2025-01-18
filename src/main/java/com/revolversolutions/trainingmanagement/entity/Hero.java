package com.revolversolutions.trainingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Hero")
@Table(name = "hero")
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String heroId;
    private String heroTitle;
    private String subtitle;
    private String buttonText;
    private boolean isActive;

    @OneToOne( cascade = CascadeType.REMOVE ,orphanRemoval = true)
    private ImageMetadata image;

    @ManyToOne
    @JoinColumn(name = "landing_page_id")
    private LandingPage landingPage;

}
