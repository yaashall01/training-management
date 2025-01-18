package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.dto.logistic.HotelDTO;
import com.revolversolutions.trainingmanagement.mapper.HotelDTOMapper;
import com.revolversolutions.trainingmanagement.service.LogisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
@Slf4j
public class HotelController {
    private final LogisticService logisticService;
    private final HotelDTOMapper hotelMapper;

    public HotelController(LogisticService logisticService, HotelDTOMapper hotelMapper) {
        this.logisticService = logisticService;
        this.hotelMapper = hotelMapper;
    }

    @GetMapping
    public List<HotelDTO> getAllHotels() {
        return logisticService.getAllHotels();
    }

    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
        HotelDTO createdHotel = logisticService.saveHotel(hotelDTO);
        return ResponseEntity.ok(createdHotel);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable String hotelId, @RequestBody HotelDTO hotelDTO) {
        HotelDTO updatedHotel = logisticService.updateHotel(hotelId, hotelDTO);
        return ResponseEntity.ok(updatedHotel);
    }
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String hotelId) {
        logisticService.deleteHotel(hotelId);
        return ResponseEntity.noContent().build();
    }

}
