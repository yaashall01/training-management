package com.revolversolutions.trainingmanagement.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LogisticDTO {

    private String logisticsId;
    private List<HotelDTO> hotels;
    private List<PickupPointDTO> pickupPoints;
    private List<TransportDTO> transports;

}
