package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.ReviewDTO;
import com.revolversolutions.trainingmanagement.entity.Review;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(String programId, ReviewDTO reviewDTO);

    ReviewDTO getReviewById(String reviewId);

    ReviewDTO updateReview(String programId, ReviewDTO reviewDTO);

    List<ReviewDTO> getReviewsByProgramId(String programId);

    void deleteReview(String reviewId);

    //Review getReviewEntityById(Long reviewId);
}
