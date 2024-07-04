package com.revolversolutions.trainingmanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity(name = "Certificate")
@Table(name = "certificates")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Certificate {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String certificateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private TrainingProgram program;

    //TODO: Add leve of certificate and other related details (it need a real certificate to inspire)

    @Lob
    @Column(name = "certificate_file")
    private byte[] certificateFile;

    @Column(name = "issued_date")
    private LocalDate issuedDate;
}
