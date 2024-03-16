package com.nurali.finalcase.service;

import com.nurali.finalcase.dto.RestaurantDTO;
import com.nurali.finalcase.entity.Restaurant;
import com.nurali.finalcase.entity.Review;

import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAllRestaurants();

    Restaurant getRestaurantById(Long restaurantId);

    Restaurant createRestaurant(RestaurantDTO dto);

    Restaurant updateRestaurant(Long restaurantId, RestaurantDTO updatedRestaurant);

    void deleteRestaurant(Long restaurantId);

    void addReviewToRestaurant(Long restaurantId, Review review);

    List<Review> getReviewsForRestaurant(Long restaurantId);
}
