package com.revolversolutions.trainingmanagement.serviceImpl;


import com.revolversolutions.trainingmanagement.dto.LogisticDTO;
import com.revolversolutions.trainingmanagement.dto.logistic.HotelDTO;
import com.revolversolutions.trainingmanagement.dto.logistic.PickupPointDTO;
import com.revolversolutions.trainingmanagement.dto.logistic.TransportDTO;
import com.revolversolutions.trainingmanagement.entity.Hotel;
import com.revolversolutions.trainingmanagement.entity.Logistic;
import com.revolversolutions.trainingmanagement.entity.PickupPoint;
import com.revolversolutions.trainingmanagement.entity.Transport;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.HotelDTOMapper;
import com.revolversolutions.trainingmanagement.mapper.LogisticDTOMapper;
import com.revolversolutions.trainingmanagement.mapper.PickupPointDTOMapper;
import com.revolversolutions.trainingmanagement.mapper.TransportDTOMapper;
import com.revolversolutions.trainingmanagement.repository.HotelRepository;
import com.revolversolutions.trainingmanagement.repository.LogisticRepository;
import com.revolversolutions.trainingmanagement.repository.PickupPointRepository;
import com.revolversolutions.trainingmanagement.repository.TransportRepository;
import com.revolversolutions.trainingmanagement.service.LogisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LogisticServiceImpl implements LogisticService {

    private final LogisticRepository logisticRepository;
    private final HotelRepository hotelRepository;
    private final PickupPointRepository pickupPointRepository;
    private final LogisticDTOMapper logisticMapper;
    private final HotelDTOMapper hotelMapper;
    private final TransportDTOMapper transportMapper;
    private final PickupPointDTOMapper pickupPointMapper;
    private final TransportRepository transportRepository;


    @Autowired
    public LogisticServiceImpl(LogisticRepository logisticRepository,
                               LogisticDTOMapper logisticMapper,
                               HotelDTOMapper hotelMapper,
                               TransportDTOMapper transportMapper,
                               PickupPointDTOMapper pickupPointMapper ,
                               HotelRepository hotelRepository,
                               PickupPointRepository pickupPointRepository,
                               TransportRepository transportRepository) {
        this.logisticRepository = logisticRepository;
        this.hotelRepository = hotelRepository;
        this.logisticMapper = logisticMapper;
        this.hotelMapper = hotelMapper;
        this.transportMapper = transportMapper;
        this.pickupPointMapper = pickupPointMapper;
        this.pickupPointRepository = pickupPointRepository;
        this.transportRepository = transportRepository;
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
        List<Hotel> hotels = hotelRepository.findAllById(
                logisticsDto.getHotels()
                        .stream()
                        .map(HotelDTO::getHotelId)
                        .collect(Collectors.toList())
        );
        List<Transport> transports = transportRepository.findAllById(
                logisticsDto.getTransports()
                        .stream()
                        .map(TransportDTO::getTransportId)
                        .collect(Collectors.toList())

        );
        List<PickupPoint> pickupPoints = pickupPointRepository.findAllById(
                logisticsDto.getPickupPoints()
                        .stream()
                        .map(PickupPointDTO::getPickupPointId)
                        .collect(Collectors.toList())

        );
        logistic.setHotels(hotels);
        logistic.setTransports(transports);
        logistic.setPickupPoints(pickupPoints);
        Logistic savedLogistics = logisticRepository.save(logistic);
        return logisticMapper.toDto(savedLogistics);
    }

    @Override
    @Transactional
    public LogisticDTO updateLogistic(String logisticsId, LogisticDTO logisticDTO) {
        Logistic existingLogistic = logisticRepository.findByLogisticsId(logisticsId)
                .orElseThrow(() -> new ResourceNotFoundException("Logistic not found with id: " + logisticsId));

        List<Hotel> hotels = hotelRepository.findAllById(
                logisticDTO.getHotels()
                        .stream()
                                    .map(HotelDTO::getHotelId)
                                    .collect(Collectors.toList())
        );
        List<Transport> transports = transportRepository.findAllById(
                logisticDTO.getTransports()
                        .stream()
                            .map(TransportDTO::getTransportId)
                            .collect(Collectors.toList())

        );
        List<PickupPoint> pickupPoints = pickupPointRepository.findAllById(
                logisticDTO.getPickupPoints()
                        .stream()
                            .map(PickupPointDTO::getPickupPointId)
                            .collect(Collectors.toList())

        );

        existingLogistic.setName(logisticDTO.getName());
        existingLogistic.setHotels(hotels);
        existingLogistic.setTransports(transports);
        existingLogistic.setPickupPoints(pickupPoints);

        Logistic updatedLogistic = logisticRepository.save(existingLogistic);
        return logisticMapper.toDto(updatedLogistic);
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

    @Override
    public HotelDTO saveHotel(HotelDTO hotel) {
        Hotel hotelEntity = hotelMapper.toEntity(hotel);
        Hotel savedHotel = hotelRepository.save(hotelEntity);
        return hotelMapper.toDto(savedHotel);
    }

    @Override
    public HotelDTO getHotelById(String id) {
        return hotelRepository.findByHotelId(id)
                .map(hotelMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found for this id :: " + id));
    }

    @Override
    public HotelDTO updateHotel(String id, HotelDTO hotel) {
        Hotel existingHotel = hotelRepository.findByHotelId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));

        existingHotel.setName(hotel.getName());
        existingHotel.setPhone(hotel.getPhone());
        existingHotel.setEmail(hotel.getEmail());
        existingHotel.setAddress(hotel.getAddress());
        existingHotel.setWebsite(hotel.getWebsite());
        existingHotel.setPriceSingle(hotel.getPriceSingle());
        existingHotel.setPriceDouble(hotel.getPriceDouble());
        existingHotel.setActive(hotel.isActive());

        Hotel savedHotel = hotelRepository.save(existingHotel);
        return hotelMapper.toDto(savedHotel);
    }

    @Override
    @Transactional
    public void deleteHotel(String id) {
        log.info("delete Hotel with id : {} ", id);
        Hotel existingHotel = hotelRepository.findByHotelId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found for this id :: " + id));
        hotelRepository.delete(existingHotel);
        log.info("Hotel with id : {} is deleted", id);

    }

    @Override
    public List<HotelDTO> getAllHotels() {
        return hotelMapper.toDtos(hotelRepository.findAll());
    }

    @Override
    public PickupPointDTO savePickupPoint(PickupPointDTO pickupPoint) {
        PickupPoint pickupPointEntity = pickupPointMapper.toEntity(pickupPoint);
        PickupPoint savedPickupPoint = pickupPointRepository.save(pickupPointEntity);
        return pickupPointMapper.toDto(savedPickupPoint);
    }

    @Override
    public PickupPointDTO getPickupPointById(String id) {
        return pickupPointRepository.findByPickupPointId(id)
                .map(pickupPointMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Pickup Point not found for this id :: " + id));
    }

    @Override
    public PickupPointDTO updatePickupPoint(String id, PickupPointDTO pickupPoint) {
        PickupPoint existingPickupPoint = pickupPointRepository.findByPickupPointId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pickup Point not found with id: " + id));
        existingPickupPoint.setLocation(pickupPoint.getLocation());
        existingPickupPoint.setName(pickupPoint.getName());
        existingPickupPoint.setLocationUrl(pickupPoint.getLocationUrl());
        PickupPoint updatedPickupPoint = pickupPointRepository.save(existingPickupPoint);
        return pickupPointMapper.toDto(updatedPickupPoint);
    }

    @Override
    public List<PickupPointDTO> getAllPickupPoints() {
        return pickupPointMapper.toDtos(pickupPointRepository.findAll());
    }

    @Override
    public void deletePickupPoint(String id) {
        log.info("delete Pickup Point with id : {} ", id);
        PickupPoint existingPickupPoint = pickupPointRepository.findByPickupPointId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pickup Point not found for this id :: " + id));
        pickupPointRepository.delete(existingPickupPoint);
        log.info("Pickup Point with id : {} is deleted", id);
    }

    @Override
    public TransportDTO saveTransport(TransportDTO transport) {
        Transport transportEntity = transportMapper.toEntity(transport);
        Transport createdTransport = transportRepository.save(transportEntity);
        return transportMapper.toDto(createdTransport);
    }

    @Override
    public TransportDTO getTransportById(String id) {
        return transportRepository.findByTransportId(id)
                .map(transportMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found for this id :: " + id));
    }

    @Override
    public TransportDTO updateTransport(String id, TransportDTO transport) {
        Transport existingTransport = transportRepository.findByTransportId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found with id: " + id));
        existingTransport.setType(transport.getType());
        existingTransport.setDriver(transport.getDriver());
        existingTransport.setDetails(transport.getDetails());
        Transport updatedTransport = transportRepository.save(existingTransport);
        return transportMapper.toDto(updatedTransport);
    }

    @Override
    public void deleteTransport(String id) {
        log.info("delete Transport with id : {} ", id);
        Transport existingTransport = transportRepository.findByTransportId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found with id: " + id));
        transportRepository.delete(existingTransport);
        log.info("Transport with id : {} is deleted", id);
    }

    @Override
    public List<TransportDTO> getAllTransports() {
        return transportMapper.toDtos(transportRepository.findAll());
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
