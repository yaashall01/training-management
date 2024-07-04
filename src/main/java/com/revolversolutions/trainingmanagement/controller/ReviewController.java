package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.aspect.UserActivityLog;
import com.revolversolutions.trainingmanagement.dto.ReviewDTO;
import com.revolversolutions.trainingmanagement.serviceImpl.ReviewServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{programId}")
    public ResponseEntity<ReviewDTO> createProgram(@PathVariable String programId , @RequestBody ReviewDTO reviewDTO){
        ReviewDTO createdReview = reviewService.createReview(programId, reviewDTO);
        return new ResponseEntity<>(createdReview , HttpStatus.CREATED);
    }
    @DeleteMapping("/{reviewId}")
    @UserActivityLog(action = "Review Deleted")
    public ResponseEntity<Void> deleteProgram(@PathVariable("id") String reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{programId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProgram(@PathVariable String programId){
        return ResponseEntity.ok(reviewService.getReviewsByProgramId(programId));
    }
}
