package com.revolversolutions.trainingmanagement.mapper;


import com.revolversolutions.trainingmanagement.dto.LogisticDTO;
import com.revolversolutions.trainingmanagement.entity.Logistic;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LogisticDTOMapper implements EntityDTOMapper<Logistic, LogisticDTO>{

    private final ModelMapper modelMapper;
    private final HotelDTOMapper hotelDTOMapper;
    private final TransportDTOMapper transportDTOMapper ;
    private final PickupPointDTOMapper pickupPointDTOMapper;

    public LogisticDTOMapper(ModelMapper modelMapper, HotelDTOMapper hotelDTOMapper, TransportDTOMapper transportDTOMapper, PickupPointDTOMapper pickupPointDTOMapper) {
        this.modelMapper = modelMapper;
        this.hotelDTOMapper = hotelDTOMapper;
        this.transportDTOMapper = transportDTOMapper;
        this.pickupPointDTOMapper = pickupPointDTOMapper;
    }



    @Override
    public LogisticDTO toDto(Logistic entity) {
        LogisticDTO logisticsDto = modelMapper.map(entity, LogisticDTO.class);
        logisticsDto.setHotels(hotelDTOMapper.toDtos(entity.getHotels()));
        logisticsDto.setPickupPoints(pickupPointDTOMapper.toDtos(entity.getPickupPoints()));
        logisticsDto.setTransports(transportDTOMapper.toDtos(entity.getTransports()));
        return logisticsDto;
    }

    @Override
        public Logistic toEntity(LogisticDTO logisticsDto) {
        Logistic logistics = modelMapper.map(logisticsDto, Logistic.class);
        logistics.setHotels(hotelDTOMapper.toEntities(logisticsDto.getHotels()));
        logistics.setPickupPoints(pickupPointDTOMapper.toEntities(logisticsDto.getPickupPoints()));
        logistics.setTransports(transportDTOMapper.toEntities(logisticsDto.getTransports()));
        return logistics;
    }
}
