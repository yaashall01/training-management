package com.revolversolutions.trainingmanagement.mapper;

import com.revolversolutions.trainingmanagement.dto.SessionDTO;
import com.revolversolutions.trainingmanagement.entity.Session;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SessionDTOMapper implements EntityDtoMapper<Session , SessionDTO>{
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public SessionDTO toDto(Session entity) {
        return modelMapper.map(entity , SessionDTO.class);
    }

    @Override
    public Session toEntity(SessionDTO dto) {
        return modelMapper.map(dto , Session.class);
    }
}
