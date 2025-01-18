package com.revolversolutions.trainingmanagement.mapper;

import com.revolversolutions.trainingmanagement.dto.LandingPageDTO;
import com.revolversolutions.trainingmanagement.entity.LandingPage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LandingPageDTOMapper implements EntityDTOMapper<LandingPage, LandingPageDTO> {
    private final ModelMapper modelMapper;

    public LandingPageDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public LandingPageDTO toDto(LandingPage entity) {
        return modelMapper.map(entity , LandingPageDTO.class);
    }

    @Override
    public LandingPage toEntity(LandingPageDTO dto) {
        return modelMapper.map(dto , LandingPage.class);
    }
}
