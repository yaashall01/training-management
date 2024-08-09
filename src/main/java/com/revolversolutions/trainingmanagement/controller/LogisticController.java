package com.revolversolutions.trainingmanagement.controller;


import com.revolversolutions.trainingmanagement.dto.LogisticDTO;
import com.revolversolutions.trainingmanagement.service.LogisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logistic")
@Slf4j
public class LogisticController {

    private final LogisticService logisticService;
//    private final HotelDTOMapper hotelMapper;
//    private final TransportDTOMapper transportMapper;
//    private final PickupPointDTOMapper pickupPointMapper;

    public LogisticController(LogisticService logisticService) {
        this.logisticService = logisticService;
    }


    @GetMapping
    public List<LogisticDTO> getAllLogistics() {
        return logisticService.getAllLogistics();
    }

    @GetMapping("/{logisticsId}")
    public ResponseEntity<LogisticDTO> getLogisticsById(@PathVariable String logisticsId) {
        LogisticDTO logisticsDto = logisticService.getLogisticById(logisticsId);
        if (logisticsDto != null) {
            return ResponseEntity.ok(logisticsDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<LogisticDTO> createLogistics(@RequestBody LogisticDTO logisticsDto) {
        LogisticDTO createdLogistics = logisticService.saveLogistic(logisticsDto);
        return ResponseEntity.ok(createdLogistics);
    }

    //TODO: Fix update it save a new record each time we update
    @PutMapping("/{logisticsId}")
    public ResponseEntity<LogisticDTO> updateLogistics(@PathVariable String logisticsId, @RequestBody LogisticDTO logisticsDto) {
        LogisticDTO updatedLogistic = logisticService.updateLogistic(logisticsId, logisticsDto);
        return ResponseEntity.ok(updatedLogistic);
    }


    @DeleteMapping("/{logisticsId}")
    public ResponseEntity<Void> deleteLogistic(@PathVariable String logisticsId) {
        LogisticDTO logisticsDto = logisticService.getLogisticById(logisticsId);
        System.out.println(logisticsDto);
        if (logisticsDto != null) {
            logisticService.deleteLogistic(logisticsId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*

    @PostMapping("/{logisticId}/hotels")
    public ResponseEntity<LogisticDto> addHotelToLogistic(@PathVariable Long logisticId, @RequestBody HotelDto hotelDto) {
        Hotel hotel = hotelMapper.toEntity(hotelDto);
        Logistic logistic = logisticService.addHotelToLogistic(logisticId, hotel);
        return ResponseEntity.ok(logisticMapper.toDto(logistic));
    }

    @DeleteMapping("/{logisticId}/hotels/{hotelId}")
    public ResponseEntity<LogisticDto> removeHotelFromLogistic(@PathVariable Long logisticId, @PathVariable Long hotelId) {
        Logistic logistic = logisticService.removeHotelFromLogistic(logisticId, hotelId);
        return ResponseEntity.ok(logisticMapper.toDto(logistic));
    }

    @PostMapping("/{logisticId}/transports")
    public ResponseEntity<LogisticDto> addTransportToLogistic(@PathVariable Long logisticId, @RequestBody TransportDto transportDto) {
        Transport transport = transportMapper.toEntity(transportDto);
        Logistic logistic = logisticService.addTransportToLogistic(logisticId, transport);
        return ResponseEntity.ok(logisticMapper.toDto(logistic));
    }

    @DeleteMapping("/{logisticId}/transports/{transportId}")
    public ResponseEntity<LogisticDto> removeTransportFromLogistic(@PathVariable Long logisticId, @PathVariable Long transportId) {
        Logistic logistic = logisticService.removeTransportFromLogistic(logisticId, transportId);
        return ResponseEntity.ok(logisticMapper.toDto(logistic));
    }

    @PostMapping("/{logisticId}/pickuppoints")
    public ResponseEntity<LogisticDto> addPickupPointToLogistic(@PathVariable Long logisticId, @RequestBody PickupPointDto pickupPointDto) {
        PickupPoint pickupPoint = pickupPointMapper.toEntity(pickupPointDto);
        Logistic logistic = logisticService.addPickupPointToLogistic(logisticId, pickupPoint);
        return ResponseEntity.ok(logisticMapper.toDto(logistic));
    }

    @DeleteMapping("/{logisticId}/pickuppoints/{pickupPointId}")
    public ResponseEntity<LogisticDto> removePickupPointFromLogistic(@PathVariable Long logisticId, @PathVariable Long pickupPointId) {
        Logistic logistic = logisticService.removePickupPointFromLogistic(logisticId, pickupPointId);
        return ResponseEntity.ok(logisticMapper.toDto(logistic));
    }
     */


}
