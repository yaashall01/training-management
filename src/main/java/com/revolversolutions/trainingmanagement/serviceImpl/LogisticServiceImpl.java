package com.revolversolutions.trainingmanagement.serviceImpl;


import com.revolversolutions.trainingmanagement.dto.LogisticDTO;
import com.revolversolutions.trainingmanagement.entity.Logistic;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.HotelDTOMapper;
import com.revolversolutions.trainingmanagement.mapper.LogisticDTOMapper;
import com.revolversolutions.trainingmanagement.mapper.PickupPointDTOMapper;
import com.revolversolutions.trainingmanagement.mapper.TransportDTOMapper;
import com.revolversolutions.trainingmanagement.repository.LogisticRepository;
import com.revolversolutions.trainingmanagement.service.LogisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class LogisticServiceImpl implements LogisticService {

    private final LogisticRepository logisticRepository;
    private final LogisticDTOMapper logisticMapper;
    private final HotelDTOMapper hotelMapper;
    private final TransportDTOMapper transportMapper;
    private final PickupPointDTOMapper pickupPointMapper;


    @Autowired
    public LogisticServiceImpl(LogisticRepository logisticRepository, LogisticDTOMapper logisticMapper, HotelDTOMapper hotelMapper, TransportDTOMapper transportMapper, PickupPointDTOMapper pickupPointMapper) {
        this.logisticRepository = logisticRepository;
        this.logisticMapper = logisticMapper;
        this.hotelMapper = hotelMapper;
        this.transportMapper = transportMapper;
        this.pickupPointMapper = pickupPointMapper;
    }


    @Override
    public List<LogisticDTO> getAllLogistics() {
        return logisticMapper.toDtos(logisticRepository.findAll());
    }

    @Override
    public LogisticDTO getLogisticById(String id) {
        return logisticRepository.findByLogisticsId(id)
                .map(logisticMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Logistics not found for this id :: " + id));
    }

    @Override
    public LogisticDTO saveLogistic(LogisticDTO logisticsDto) {
        Logistic logistic = logisticMapper.toEntity(logisticsDto);
        logistic = logisticRepository.save(logistic);
        return logisticMapper.toDto(logistic);
    }

    @Override
    @Transactional
    public LogisticDTO updateLogistic(String logisticsId, LogisticDTO logisticDTO) {
        Logistic existingLogistic = logisticRepository.findByLogisticsId(logisticsId)
                .orElseThrow(() -> new ResourceNotFoundException("Logistic not found with id: " + logisticsId));


        Logistic updatedLogistic = logisticMapper.toEntity(logisticDTO);
        updatedLogistic.setId(existingLogistic.getId());
        updatedLogistic.setLogisticsId(existingLogistic.getLogisticsId());

        Logistic savedLogistic = logisticRepository.save(updatedLogistic);
        return logisticMapper.toDto(savedLogistic);
    }

    private void updateLogisticFields(Logistic existingLogistic, LogisticDTO logisticDTO) {
        existingLogistic.getHotels().clear();
        existingLogistic.getHotels().addAll(hotelMapper.toEntities(logisticDTO.getHotels()));

        existingLogistic.getPickupPoints().clear();
        existingLogistic.getPickupPoints().addAll(pickupPointMapper.toEntities(logisticDTO.getPickupPoints()));

        existingLogistic.getTransports().clear();
        existingLogistic.getTransports().addAll(transportMapper.toEntities(logisticDTO.getTransports()));
    }

    @Override
    @Transactional
    public void deleteLogistic(String id) {
        log.info("delete logistic with id : {} ", id);
        Logistic existingLogistics = logisticRepository.findByLogisticsId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Logistics not found for this id :: " + id));
        logisticRepository.delete(existingLogistics);
        log.info("logistic with id : {} is deleted", id);
    }

/*

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private PickupPointRepository pickupPointRepository;

    public Logistic addHotelToLogistic(Long logisticId, Hotel hotel) {
        Logistic logistic = logisticRepository.findById(logisticId).orElseThrow(() -> new RuntimeException("Logistic not found"));
        logistic.getHotels().add(hotel);
        hotelRepository.save(hotel);
        return logisticRepository.save(logistic);
    }

    public Logistic removeHotelFromLogistic(Long logisticId, Long hotelId) {
        Logistic logistic = logisticRepository.findById(logisticId).orElseThrow(() -> new RuntimeException("Logistic not found"));
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found"));
        logistic.getHotels().remove(hotel);
        hotelRepository.delete(hotel);
        return logisticRepository.save(logistic);
    }

    public Logistic addTransportToLogistic(Long logisticId, Transport transport) {
        Logistic logistic = logisticRepository.findById(logisticId).orElseThrow(() -> new RuntimeException("Logistic not found"));
        logistic.getTransports().add(transport);
        transportRepository.save(transport);
        return logisticRepository.save(logistic);
    }

    public Logistic removeTransportFromLogistic(Long logisticId, Long transportId) {
        Logistic logistic = logisticRepository.findById(logisticId).orElseThrow(() -> new RuntimeException("Logistic not found"));
        Transport transport = transportRepository.findById(transportId).orElseThrow(() -> new RuntimeException("Transport not found"));
        logistic.getTransports().remove(transport);
        transportRepository.delete(transport);
        return logisticRepository.save(logistic);
    }

    public Logistic addPickupPointToLogistic(Long logisticId, PickupPoint pickupPoint) {
        Logistic logistic = logisticRepository.findById(logisticId).orElseThrow(() -> new RuntimeException("Logistic not found"));
        logistic.getPickupPoints().add(pickupPoint);
        pickupPointRepository.save(pickupPoint);
        return logisticRepository.save(logistic);
    }

    public Logistic removePickupPointFromLogistic(Long logisticId, Long pickupPointId) {
        Logistic logistic = logisticRepository.findById(logisticId).orElseThrow(() -> new RuntimeException("Logistic not found"));
        PickupPoint pickupPoint = pickupPointRepository.findById(pickupPointId).orElseThrow(() -> new RuntimeException("PickupPoint not found"));
        logistic.getPickupPoints().remove(pickupPoint);
        pickupPointRepository.delete(pickupPoint);
        return logisticRepository.save(logistic);
    }

 */


}
