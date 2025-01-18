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
@Entity(name = "Transport")
@Table(name = "transport")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transportId;

    private String type;

    private String driver;

    private String details;

    @ManyToOne
    @JoinColumn(name = "logistics_id", insertable = false, updatable = false)
    private Logistic logistics;
}
