package com.nurali.finalcase.controller;

import com.nurali.finalcase.dto.RestaurantDTO;
import com.nurali.finalcase.dto.ReviewDTO;
import com.nurali.finalcase.entity.Restaurant;
import com.nurali.finalcase.entity.Review;
import com.nurali.finalcase.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@Tag(name = "Restaurant Management")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @Operation(summary = "Create a new restaurant")
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }

    @GetMapping("/{restaurantId}")
    @Operation(summary = "Get restaurant by ID")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping
    @Operation(summary = "Get all restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @PutMapping("/{restaurantId}")
    @Operation(summary = "Update restaurant information")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long restaurantId, @Valid @RequestBody RestaurantDTO restaurantDTO) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurantDTO);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/{restaurantId}")
    @Operation(summary = "Delete restaurant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{restaurantId}/reviews")
    @Operation(summary = "Get reviews for a restaurant")
    public ResponseEntity<List<Review>> getReviewsForRestaurant(
            @Parameter(description = "ID of the restaurant") @PathVariable Long restaurantId) {
        List<Review> reviews = restaurantService.getReviewsForRestaurant(restaurantId);
        return ResponseEntity.ok(reviews);
    }
}
