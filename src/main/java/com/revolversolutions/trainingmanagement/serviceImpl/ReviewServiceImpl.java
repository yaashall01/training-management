package com.revolversolutions.trainingmanagement.serviceImpl;

import com.revolversolutions.trainingmanagement.dto.ReviewDTO;
import com.revolversolutions.trainingmanagement.entity.Review;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.ReviewDTOMpper;
import com.revolversolutions.trainingmanagement.repository.ReviewRepository;
import com.revolversolutions.trainingmanagement.repository.TrainingProgramRepository;
import com.revolversolutions.trainingmanagement.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final TrainingProgramRepository programRepository;
    private final ReviewDTOMpper reviewDTOMpper;


    @Override
    public ReviewDTO createReview(String programId, ReviewDTO reviewDTO) {
        Review newReview = reviewDTOMpper.toEntity(reviewDTO);
        TrainingProgram trainingProgram = programRepository.findByProgramId(programId)
                        .orElseThrow((() -> new ResourceNotFoundException("Can't found program with id : " + programId)));
        newReview.setProgram(trainingProgram);
        Review savedReview = reviewRepository.save(newReview);
        log.info("Review created successfully");
        return reviewDTOMpper.toDto(savedReview);
    }

    @Override
    public ReviewDTO getReviewById(String reviewId) {
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " +reviewId));
        log.info("Fetching Review by id: {} " , reviewId);
        return reviewDTOMpper.toDto(review);
    }

    @Override
    public ReviewDTO updateReview(String programId, ReviewDTO reviewDTO){
        return null;
    }

    @Override
    public List<ReviewDTO> getReviewsByProgramId(String programId) {
        TrainingProgram program = programRepository.findByProgramId(programId)
                .orElseThrow((() -> new ResourceNotFoundException("Can't found program with id : " + programId)));
        log.info("Fetching program id: {}, title: {} reviews", programId, program.getTitle());
        return reviewDTOMpper.toDtos(program.getReviews());
    }

    @Override
    public void deleteReview(String reviewId) {
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " +reviewId));
        reviewRepository.delete(review);
        log.info("Review with id: {} has been deleted successfully", reviewId);
    }

    /*@Override
    public Review getReviewEntityById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ResourceNotFoundException("Review not found with id: " +reviewId));
        return review;
    }

     */
}
