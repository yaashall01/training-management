package com.revolversolutions.trainingmanagement.mapper;

import com.revolversolutions.trainingmanagement.dto.PickupPointDTO;
import com.revolversolutions.trainingmanagement.entity.PickupPoint;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PickupPointDTOMapper implements EntityDTOMapper<PickupPoint, PickupPointDTO>{

    private final ModelMapper modelMapper;

    public PickupPointDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public PickupPointDTO toDto(PickupPoint entity) {
        return modelMapper.map(entity, PickupPointDTO.class);
    }

    @Override
    public PickupPoint toEntity(PickupPointDTO dto) {
        return modelMapper.map(dto, PickupPoint.class);
    }
}
