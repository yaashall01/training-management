package com.revolversolutions.trainingmanagement.dto;

import com.revolversolutions.trainingmanagement.dto.user.UserReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private String reviewId;
    private String content;
    private double rating;
    private UserReview user;

}
