package com.revolversolutions.trainingmanagement.mapper;

import com.revolversolutions.trainingmanagement.dto.ReviewDTO;
import com.revolversolutions.trainingmanagement.entity.Review;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewDTOMpper implements EntityDTOMapper<Review, ReviewDTO>{
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
        return EntityDTOMapper.super.toDtos(entities);
    }

    @Override
    public List<Review> toEntities(List<ReviewDTO> dtos) {
        return EntityDTOMapper.super.toEntities(dtos);
    }
}
