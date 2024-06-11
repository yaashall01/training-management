package com.revolversolutions.trainingmanagement.dto;

import com.revolversolutions.trainingmanagement.enums.UserGender;
import com.revolversolutions.trainingmanagement.enums.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotEmpty(message = "First name should not be empty")
    @Size(min = 2, max = 20, message = "First name should have be min 2 and max 20")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    @Size(min = 2, max = 20, message = "Last name should have be min 2 and max 20")
    private String lastName;

    @Past(message = "Date of birth should be in the past")
    private LocalDate dob;

    @NotEmpty(message = "User name should not be empty")
    @Size(min = 2, max = 20, message = "User name should have be min 2 and max 20")
    private String userName;

    @NotEmpty(message = "Should not be empty")
    @Email(message = "Should be a valid email")
    private String email;

    @NotEmpty(message = "Phone number should not be empty")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Phone number should be valid")
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @NotEmpty(message = "Address should not be empty")
    private String address;

    @Size(min = 5, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", message = "Password must contain at least one letter and one number")
    private String password;

    private UserRole userRole;

}
