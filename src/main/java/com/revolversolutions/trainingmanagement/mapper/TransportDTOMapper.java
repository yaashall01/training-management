package com.revolversolutions.trainingmanagement.mapper;


import com.revolversolutions.trainingmanagement.dto.TransportDTO;
import com.revolversolutions.trainingmanagement.entity.Transport;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransportDTOMapper implements EntityDTOMapper<Transport, TransportDTO>{

    private final ModelMapper modelMapper;

    public TransportDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public TransportDTO toDto(Transport entity) {
        return modelMapper.map(entity, TransportDTO.class);
    }

    @Override
    public Transport toEntity(TransportDTO dto) {
        return modelMapper.map(dto, Transport.class);
    }
}
