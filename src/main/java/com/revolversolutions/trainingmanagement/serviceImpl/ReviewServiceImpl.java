package com.revolversolutions.trainingmanagement.serviceImpl;

import com.revolversolutions.trainingmanagement.dto.ReviewDTO;
import com.revolversolutions.trainingmanagement.entity.Review;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import com.revolversolutions.trainingmanagement.mapper.ReviewDTOMpper;
import com.revolversolutions.trainingmanagement.repository.ReviewRepository;
import com.revolversolutions.trainingmanagement.repository.TrainingProgramRepository;
import com.revolversolutions.trainingmanagement.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final TrainingProgramRepository programRepository;
    private final ReviewDTOMpper reviewDTOMpper;
    @Override
    public ReviewDTO createReview(Long programId, Long userId, ReviewDTO reviewDTO) {
        Review newReview = reviewDTOMpper.toEntity(reviewDTO);
        TrainingProgram trainingProgram = programRepository.findById(programId)
                .orElse(null);
        newReview.setProgram(trainingProgram);
        Review savedReview = reviewRepository.save(newReview);
        log.info("Review created successfully");
        return reviewDTOMpper.toDto(savedReview);
    }
    @Override
    public ReviewDTO getReviewById(Long reviewId) {
        Review review = getReviewEntityById(reviewId);
        log.info("Fetching Review by id: {} " , reviewId);
        return reviewDTOMpper.toDto(review);
    }

    @Override
    public Review getReviewEntityById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        return review;
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = getReviewEntityById(reviewId);
        reviewRepository.delete(review);
        log.info("Review with id: {} has been deleted successfully", reviewId);
    }
}