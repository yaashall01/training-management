package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.dto.logistic.PickupPointDTO;
import com.revolversolutions.trainingmanagement.mapper.PickupPointDTOMapper;
import com.revolversolutions.trainingmanagement.service.LogisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pickUpPoint")
@Slf4j
public class PickupPointController {
    private final LogisticService logisticService;
    private final PickupPointDTOMapper pickupPointDTOMapper;

    public PickupPointController(LogisticService logisticService,
                                 PickupPointDTOMapper pickupPointDTOMapper) {
        this.logisticService = logisticService;
        this.pickupPointDTOMapper = pickupPointDTOMapper;
    }

    @GetMapping
    public List<PickupPointDTO> getAllPickupPoints() {
        return logisticService.getAllPickupPoints();
    }

    @PostMapping
    public ResponseEntity<PickupPointDTO> createPickupPoint(@RequestBody PickupPointDTO pickupPointDTO) {
        PickupPointDTO createdPickupPoint = logisticService.savePickupPoint(pickupPointDTO);
        return ResponseEntity.ok(createdPickupPoint);
    }

    @PutMapping("/{pickupPointId}")
    public ResponseEntity<PickupPointDTO> updatePickupPoint(@PathVariable String pickupPointId, @RequestBody PickupPointDTO pickupPointDTO) {
        PickupPointDTO updatedPickupPoint = logisticService.updatePickupPoint(pickupPointId, pickupPointDTO);
        return ResponseEntity.ok(updatedPickupPoint);
    }
    @DeleteMapping("/{pickupPointId}")
    public ResponseEntity<Void> deletePickupPoint(@PathVariable String pickupPointId) {
        logisticService.deletePickupPoint(pickupPointId);
        return ResponseEntity.noContent().build();
    }

}
