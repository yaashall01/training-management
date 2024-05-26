package com.revolversolutions.trainingmanagement.mapper;


import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.entity.Enrollment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentDTOMapper implements EntityDTOMapper<Enrollment, EnrollmentDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public EnrollmentDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public EnrollmentDTO toDto(Enrollment entity) {
        return modelMapper.map(entity, EnrollmentDTO.class);
    }

    @Override
    public Enrollment toEntity(EnrollmentDTO dto) {
        return modelMapper.map(dto, Enrollment.class);
    }
}
