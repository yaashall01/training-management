package com.revolversolutions.trainingmanagement.mapper;

import com.revolversolutions.trainingmanagement.dto.ImageMetadataDTO;
import com.revolversolutions.trainingmanagement.entity.ImageMetadata;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageMetadataDTOMapper implements EntityDTOMapper<ImageMetadata , ImageMetadataDTO>{
    private final ModelMapper modelMapper;

    public ImageMetadataDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ImageMetadataDTO toDto(ImageMetadata entity) {
        return modelMapper.map(entity, ImageMetadataDTO.class);
    }

    @Override
    public ImageMetadata toEntity(ImageMetadataDTO dto) {
        return modelMapper.map(dto, ImageMetadata.class);
    }
}
