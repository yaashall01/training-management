package com.revolversolutions.trainingmanagement.mapper;


import com.revolversolutions.trainingmanagement.dto.HotelDTO;
import com.revolversolutions.trainingmanagement.entity.Hotel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HotelDTOMapper implements EntityDTOMapper<Hotel, HotelDTO>{

    private final ModelMapper modelMapper;

    public HotelDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }



    @Override
    public HotelDTO toDto(Hotel entity) {
        return modelMapper.map(entity, HotelDTO.class);
    }

    @Override
    public Hotel toEntity(HotelDTO dto) {
        return modelMapper.map(dto, Hotel.class);
    }
}
