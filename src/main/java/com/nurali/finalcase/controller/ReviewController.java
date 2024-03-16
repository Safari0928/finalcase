package com.nurali.finalcase.controller;

import com.nurali.finalcase.dto.ReviewDTO;
import com.nurali.finalcase.entity.Review;
import com.nurali.finalcase.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{restaurantId}")
    @Operation(summary = "Add review to a restaurant")
    public ResponseEntity<Review> addReviewToRestaurant(
            @Parameter(description = "ID of the restaurant") @PathVariable Long restaurantId,
            @Valid @RequestBody ReviewDTO reviewDTO) {
        Review createdReview = reviewService.createReview(restaurantId, reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete a review by ID")
    public ResponseEntity<Void> deleteReview(
            @Parameter(description = "ID of the review to be deleted") @PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "Update review information")
    public ResponseEntity<Review> updateReview(
            @Parameter(description = "ID of the review to be updated") @PathVariable Long reviewId,
            @Valid @RequestBody ReviewDTO reviewDTO) {
        Review updatedReview = reviewService.updateReview(reviewId, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    @GetMapping
    @Operation(summary = "Get all reviews")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }


}
