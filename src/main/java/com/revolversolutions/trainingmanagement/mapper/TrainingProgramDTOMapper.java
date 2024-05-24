package com.revolversolutions.trainingmanagement.mapper;

import com.revolversolutions.trainingmanagement.dto.TrainingProgramDTO;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainingProgramDTOMapper implements EntityDTOMapper<TrainingProgram , TrainingProgramDTO>{
    private  ModelMapper modelMapper = new ModelMapper();
    @Override
    public TrainingProgramDTO toDto(TrainingProgram entity) {
        return modelMapper.map(entity , TrainingProgramDTO.class);
    }

    @Override
    public TrainingProgram toEntity(TrainingProgramDTO dto) {
        return modelMapper.map(dto , TrainingProgram.class);
    }

    @Override
    public List<TrainingProgramDTO> toDtos(List<TrainingProgram> entities) {
        return EntityDTOMapper.super.toDtos(entities);
    }

    @Override
    public List<TrainingProgram> toEntities(List<TrainingProgramDTO> dtos) {
        return EntityDTOMapper.super.toEntities(dtos);
    }
}
