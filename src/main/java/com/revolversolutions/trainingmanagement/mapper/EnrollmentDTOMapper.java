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
        EnrollmentDTO dto = modelMapper.map(entity, EnrollmentDTO.class);
        
        // Handle file mappings manually
        if (entity.getPaymentProofFile() != null) {
            dto.setPaymentProofFileId(entity.getPaymentProofFile().getId());
            dto.setPaymentProofFileName(entity.getPaymentProofFile().getName());
        }
        
        if (entity.getPrerequisiteProofFile() != null) {
            dto.setPrerequisiteProofFileId(entity.getPrerequisiteProofFile().getId());
            dto.setPrerequisiteProofFileName(entity.getPrerequisiteProofFile().getName());
        }
        
        return dto;
    }

    @Override
    public Enrollment toEntity(EnrollmentDTO dto) {
        Enrollment entity = modelMapper.map(dto, Enrollment.class);
        
        // Note: File entities should be handled separately in the service layer
        // as they require repository lookups
        
        return entity;
    }
}
