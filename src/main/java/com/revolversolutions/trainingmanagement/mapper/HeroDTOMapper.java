package com.revolversolutions.trainingmanagement.mapper;

import com.revolversolutions.trainingmanagement.dto.HeroDTO;
import com.revolversolutions.trainingmanagement.entity.Hero;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HeroDTOMapper implements EntityDTOMapper<Hero , HeroDTO> {
    private final ModelMapper modelMapper;

    public HeroDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public HeroDTO toDto(Hero entity) {
        return modelMapper.map(entity, HeroDTO.class);
    }

    @Override
    public Hero toEntity(HeroDTO dto) {
        return modelMapper.map(dto, Hero.class);
    }
}
