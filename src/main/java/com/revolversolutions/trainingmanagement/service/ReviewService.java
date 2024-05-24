package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.ReviewDTO;
import com.revolversolutions.trainingmanagement.entity.Review;

public interface ReviewService {
    ReviewDTO createReview(Long programId, Long userId, ReviewDTO reviewDTO);

    ReviewDTO getReviewById(Long reviewId);
    Review getReviewEntityById(Long reviewId);

    void deleteReview(Long reviewId);
}
