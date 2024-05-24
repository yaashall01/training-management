package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
