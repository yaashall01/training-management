package com.revolversolutions.trainingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "LandingPage")
@Table(name = "landingPage")
public class LandingPage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String landingPageId;

    private String country;
    private String name;
    private String slogan;


    @OneToMany(
            mappedBy = "landingPage",
            orphanRemoval = true,
            cascade = CascadeType.PERSIST
    )
    private List<LService> LServices = new ArrayList<>();

    @OneToMany(
            mappedBy = "landingPage",
            orphanRemoval = true,
            cascade = CascadeType.PERSIST
    )
    private List<Hero> heros = new ArrayList<>();

    @OneToMany(
            mappedBy = "landingPage",
            orphanRemoval = true,
            cascade = CascadeType.PERSIST
    )
    private List<Cta> ctaList = new ArrayList<>();

    @Embedded
    private Heading heading;
}
