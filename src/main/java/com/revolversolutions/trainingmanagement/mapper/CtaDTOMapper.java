package com.revolversolutions.trainingmanagement.mapper;

import com.revolversolutions.trainingmanagement.dto.CtaDTO;
import com.revolversolutions.trainingmanagement.entity.Cta;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CtaDTOMapper implements EntityDTOMapper<Cta , CtaDTO> {
    private final ModelMapper modelMapper;

    public CtaDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CtaDTO toDto(Cta entity) {
        return modelMapper.map(entity, CtaDTO.class);
    }

    @Override
    public Cta toEntity(CtaDTO dto) {
        return modelMapper.map(dto, Cta.class);
    }
}
