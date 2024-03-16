package com.nurali.finalcase.service;


import com.nurali.finalcase.dto.ReviewDTO;
import com.nurali.finalcase.entity.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    Review createReview(Long restaurantId,ReviewDTO reviewDTO);

    Review updateReview(Long reviewId, ReviewDTO reviewDTO);

    void deleteReview(Long reviewId);

    List<ReviewDTO> getAllReviews();


}
