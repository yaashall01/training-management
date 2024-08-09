package com.revolversolutions.trainingmanagement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OTPResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("email")
    private String email;

}
