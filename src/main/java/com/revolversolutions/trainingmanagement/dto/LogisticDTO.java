package com.revolversolutions.trainingmanagement.dto;


import com.revolversolutions.trainingmanagement.dto.logistic.HotelDTO;
import com.revolversolutions.trainingmanagement.dto.logistic.PickupPointDTO;
import com.revolversolutions.trainingmanagement.dto.logistic.TransportDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LogisticDTO {

    private String logisticsId;
    private String name;
    private List<HotelDTO> hotels;
    private List<PickupPointDTO> pickupPoints;
    private List<TransportDTO> transports;

}
