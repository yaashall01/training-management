package com.revolversolutions.trainingmanagement.mapper;

import com.revolversolutions.trainingmanagement.dto.ReviewDTO;
import com.revolversolutions.trainingmanagement.entity.Review;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewDTOMpper implements EntityDtoMapper<Review, ReviewDTO>{
    private ModelMapper modelMapper = new ModelMapper();
    @Override
    public ReviewDTO toDto(Review entity) {
        return modelMapper.map(entity , ReviewDTO.class);
    }

    @Override
    public Review toEntity(ReviewDTO dto) {
        return modelMapper.map(dto , Review.class);
    }

    @Override
    public List<ReviewDTO> toDtos(List<Review> entities) {
        return EntityDtoMapper.super.toDtos(entities);
    }

    @Override
    public List<Review> toEntities(List<ReviewDTO> dtos) {
        return EntityDtoMapper.super.toEntities(dtos);
    }
}
