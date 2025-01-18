package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.LogisticDTO;
import com.revolversolutions.trainingmanagement.dto.logistic.HotelDTO;
import com.revolversolutions.trainingmanagement.dto.logistic.PickupPointDTO;
import com.revolversolutions.trainingmanagement.dto.logistic.TransportDTO;
import com.revolversolutions.trainingmanagement.entity.Logistic;

import java.util.List;


public interface LogisticService {
    List<LogisticDTO> getAllLogistics();

    LogisticDTO getLogisticById(String id);

    LogisticDTO updateLogistic(String id, LogisticDTO logisticDTO);

    LogisticDTO saveLogistic(LogisticDTO Logistic);

    void deleteLogistic(String id);

    // Hotel service ..

    HotelDTO saveHotel(HotelDTO hotel);
    HotelDTO getHotelById(String id);
    HotelDTO updateHotel(String id, HotelDTO hotel);
    void deleteHotel(String id);
    List<HotelDTO> getAllHotels();

    // PickUp Point service

    PickupPointDTO savePickupPoint(PickupPointDTO pickupPoint);
    PickupPointDTO getPickupPointById(String id);
    PickupPointDTO updatePickupPoint(String id, PickupPointDTO pickupPoint);
    List<PickupPointDTO> getAllPickupPoints();
    void deletePickupPoint(String id);

    // Transport service

    TransportDTO saveTransport(TransportDTO transport);
    TransportDTO getTransportById(String id);
    TransportDTO updateTransport(String id, TransportDTO transport);
    void deleteTransport(String id);
    List<TransportDTO> getAllTransports();
}
