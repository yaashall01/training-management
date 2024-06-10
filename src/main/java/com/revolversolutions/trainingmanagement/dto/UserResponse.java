package com.revolversolutions.trainingmanagement.dto;


import com.revolversolutions.trainingmanagement.entity.FileDB;
import com.revolversolutions.trainingmanagement.enums.UserGender;
import com.revolversolutions.trainingmanagement.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phone;
    private UserGender gender;
    private String address;
    private LocalDate dob;
    private LocalDateTime createdAt;
    private UserRole userRole;
    private FileDB profileImage;

}
