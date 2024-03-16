package com.nurali.finalcase.service.impl;

import com.nurali.finalcase.dto.ReviewDTO;
import com.nurali.finalcase.entity.Restaurant;
import com.nurali.finalcase.entity.Review;
import com.nurali.finalcase.repository.RestaurantRepository;
import com.nurali.finalcase.repository.ReviewRepository;
import com.nurali.finalcase.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Review createReview(Long restaurantId, ReviewDTO reviewDTO) {
        log.info("Creating review for restaurant with ID: {}", restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> {
                    log.error("Restaurant not found with id: {}", restaurantId);
                    return new RuntimeException("Restaurant not found with id: " + restaurantId);
                });

        Review review = new Review();
        review.setScore(reviewDTO.getScore());
        review.setText(reviewDTO.getText());
        review.setRestaurant(restaurant);

        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long reviewId, ReviewDTO reviewDTO) {
        log.info("Updating review with ID: {}", reviewId);
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.error("Review not found with id: {}", reviewId);
                    return new RuntimeException("Review not found with id: " + reviewId);
                });
        existingReview.setScore(reviewDTO.getScore());
        existingReview.setText(reviewDTO.getText());
        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(Long reviewId) {
        log.info("Deleting review with ID: {}", reviewId);
        reviewRepository.deleteById(reviewId);
    }

   /* @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return convertToDTOList(reviews);
    }*/

    private List<ReviewDTO> convertToDTOList(List<Review> reviews) {
        List<ReviewDTO> dtoList = new ArrayList<>();
        for (Review review : reviews) {
            dtoList.add(convertToDTO(review));
        }
        return dtoList;
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setScore(review.getScore());
        dto.setText(review.getText());
        // 设置餐厅ID或其他必要的属性
        dto.setRestaurantName(review.getRestaurant().getName());
        return dto;
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAllWithRestaurant(); // 使用 join fetch 策略加载 Restaurant
        return convertToDTOList(reviews);
    }

}
