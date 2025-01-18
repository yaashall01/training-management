package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.dto.logistic.TransportDTO;
import com.revolversolutions.trainingmanagement.mapper.TransportDTOMapper;
import com.revolversolutions.trainingmanagement.service.LogisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transport")
@Slf4j
public class TransportController {
    private final LogisticService logisticService;
    private final TransportDTOMapper transportDTOMapper;

    public TransportController(LogisticService logisticService, TransportDTOMapper transportDTOMapper) {
        this.logisticService = logisticService;
        this.transportDTOMapper = transportDTOMapper;
    }

    @GetMapping
    public List<TransportDTO> getAllTransports() {
        return logisticService.getAllTransports();
    }

    @PostMapping
    public ResponseEntity<TransportDTO> createTransport(@RequestBody TransportDTO transportDTO) {
        TransportDTO createdTransport = logisticService.saveTransport(transportDTO);
        return ResponseEntity.ok(createdTransport);
    }

    @PutMapping("/{transportId}")
    public ResponseEntity<TransportDTO> updateTransport(@PathVariable String transportId, @RequestBody TransportDTO transportDTO) {
        TransportDTO updatedTransport = logisticService.updateTransport(transportId, transportDTO);
        return ResponseEntity.ok(updatedTransport);
    }
    @DeleteMapping("/{transportId}")
    public ResponseEntity<Void> deleteTransport(@PathVariable String transportId) {
        logisticService.deleteTransport(transportId);
        return ResponseEntity.noContent().build();
    }

}
