package com.revolversolutions.trainingmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelDTO {

    private String hotelId;

    private String name;

    private double priceSingle;

    private double priceDouble;

    private String address;

    private String website;

    private String email;

    private String phone;

}
