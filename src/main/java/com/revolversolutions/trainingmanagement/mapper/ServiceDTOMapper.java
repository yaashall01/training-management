package com.revolversolutions.trainingmanagement.mapper;

import com.revolversolutions.trainingmanagement.dto.ServiceDTO;
import com.revolversolutions.trainingmanagement.entity.LService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ServiceDTOMapper implements EntityDTOMapper<LService, ServiceDTO> {
    private final ModelMapper modelMapper;

    public ServiceDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ServiceDTO toDto(LService entity) {
        return modelMapper.map(entity, ServiceDTO.class);
    }

    @Override
    public LService toEntity(ServiceDTO dto) {
        return modelMapper.map(dto, LService.class);
    }
}
