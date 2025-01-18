package com.revolversolutions.trainingmanagement.dto.logistic;

import com.revolversolutions.trainingmanagement.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelDTO {

    private String hotelId;

    private String name;

    private double priceSingle;

    private double priceDouble;

    private Address address;

    private String website;

    private String email;

    private String phone;

    private boolean isActive;


}
