package com.revolversolutions.trainingmanagement.dto;

import com.revolversolutions.trainingmanagement.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequestDTO {
    
    @NotNull
    private String userId;
    
    @NotNull
    private String programId;
    
    @NotNull
    private PaymentType paymentType;
    
    private MultipartFile paymentProofFile;
    
    private MultipartFile prerequisiteProofFile;
    
    // Optional user info updates
    private String firstName;
    private String lastName;
    private String phone;
    private String city;
    private String country;
    private String state;
    private String street;
    private String zipCode;
    
    private String notes; // Any additional notes from the user
}
