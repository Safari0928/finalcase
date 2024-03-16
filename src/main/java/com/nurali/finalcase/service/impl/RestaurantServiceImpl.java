package com.nurali.finalcase.service.impl;

import com.nurali.finalcase.dto.RestaurantDTO;
import com.nurali.finalcase.dto.ReviewDTO;
import com.nurali.finalcase.entity.Restaurant;
import com.nurali.finalcase.entity.Review;
import com.nurali.finalcase.repository.RestaurantRepository;
import com.nurali.finalcase.repository.ReviewRepository;
import com.nurali.finalcase.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAllWithReviews();
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        log.info("Fetching restaurant with ID: {}", restaurantId);
        Restaurant restaurant = restaurantRepository.getRestaurantByIdWithReviews(restaurantId);
        if (restaurant == null) {
            log.error("Restaurant not found with id: {}", restaurantId);
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        return restaurant;
    }

    @Override
    public Restaurant createRestaurant(RestaurantDTO dto) {
        log.info("Creating new restaurant");
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(dto, restaurant);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, RestaurantDTO updatedRestaurant) {
        log.info("Updating restaurant with ID: {}", restaurantId);
        try {
            Restaurant existingRestaurant = getRestaurantById(restaurantId);
            existingRestaurant.setName(updatedRestaurant.getName());
            existingRestaurant.setAddress(updatedRestaurant.getAddress());
            existingRestaurant.setLatitude(updatedRestaurant.getLatitude());
            existingRestaurant.setLongitude(updatedRestaurant.getLongitude());
            existingRestaurant.setScore(updatedRestaurant.getScore());
            return restaurantRepository.save(existingRestaurant);
        } catch (DataAccessException ex) {
            log.error("Error updating restaurant with ID: {}", restaurantId, ex);
            throw new RuntimeException("Error updating restaurant with ID: " + restaurantId, ex);
        } catch (NoSuchElementException ex) {
            log.error("Restaurant not found with ID: {}", restaurantId, ex);
            throw new RuntimeException("Restaurant not found with ID: " + restaurantId);
        }
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        log.info("Deleting restaurant with ID: {}", restaurantId);
        restaurantRepository.deleteById(restaurantId);
    }

    @Override
    public void addReviewToRestaurant(Long restaurantId, Review review) {
        log.info("Adding review to restaurant with ID: {}", restaurantId);
        Restaurant restaurant = getRestaurantById(restaurantId);
        review.setRestaurant(restaurant);
        reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsForRestaurant(Long restaurantId) {
        log.info("Fetching reviews for restaurant with ID: {}", restaurantId);
        Restaurant restaurant = getRestaurantById(restaurantId);
        return restaurant.getReviews();
    }
}
