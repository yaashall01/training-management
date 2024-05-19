package com.revolversolutions.trainingmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}