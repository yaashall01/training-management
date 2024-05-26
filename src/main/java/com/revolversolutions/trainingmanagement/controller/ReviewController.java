package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.dto.ReviewDTO;
import com.revolversolutions.trainingmanagement.serviceImpl.ReviewServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review")
@AllArgsConstructor
public class ReviewController {
    private final ReviewServiceImpl reviewService;

    @PostMapping("/{programId}")
    public ResponseEntity<ReviewDTO> createProgram(@PathVariable Long programId , @RequestBody ReviewDTO reviewDTO){
        ReviewDTO createdReview = reviewService.createReview(programId ,programId, reviewDTO);
        return new ResponseEntity<>(createdReview , HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable("id") Long id){
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
