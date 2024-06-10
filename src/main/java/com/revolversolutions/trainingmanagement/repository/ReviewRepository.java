package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewId(String reviewId);
}
