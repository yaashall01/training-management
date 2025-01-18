package com.revolversolutions.trainingmanagement.mapper;


import com.revolversolutions.trainingmanagement.dto.LogisticDTO;
import com.revolversolutions.trainingmanagement.entity.Logistic;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LogisticDTOMapper implements EntityDTOMapper<Logistic, LogisticDTO>{

    private final ModelMapper modelMapper;

    public LogisticDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public LogisticDTO toDto(Logistic entity) {
        LogisticDTO logisticsDto = modelMapper.map(entity, LogisticDTO.class);
        return logisticsDto;
    }

    @Override
        public Logistic toEntity(LogisticDTO logisticsDto) {
        Logistic logistics = modelMapper.map(logisticsDto, Logistic.class);
        return logistics;
    }
}
