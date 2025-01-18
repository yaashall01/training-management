package com.revolversolutions.trainingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Service")
@Table(name = "service")
public class LService {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String serviceId;
    private String serviceTitle;
    private String description;

    @ManyToOne
    @JoinColumn(name = "landing_page_id")
    private LandingPage landingPage;

}
