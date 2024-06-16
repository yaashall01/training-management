package com.revolversolutions.trainingmanagement.mapper;


import com.revolversolutions.trainingmanagement.dto.AttendanceDTO;
import com.revolversolutions.trainingmanagement.dto.EnrollmentDTO;
import com.revolversolutions.trainingmanagement.entity.Attendance;
import com.revolversolutions.trainingmanagement.entity.Enrollment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttendanceDTOMapper implements EntityDTOMapper<Attendance, AttendanceDTO>{

    private final ModelMapper modelMapper;

    @Autowired
    public AttendanceDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public AttendanceDTO toDto(Attendance entity) {
        return modelMapper.map(entity, AttendanceDTO.class);
    }

    @Override
    public Attendance toEntity(AttendanceDTO dto) {
        return modelMapper.map(dto, Attendance.class);
    }
}
