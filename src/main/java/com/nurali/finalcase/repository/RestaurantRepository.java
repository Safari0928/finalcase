package com.nurali.finalcase.repository;

import com.nurali.finalcase.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.reviews")
    List<Restaurant> findAllWithReviews();

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.reviews WHERE r.id = :restaurantId")
    Restaurant getRestaurantByIdWithReviews(@Param("restaurantId") Long restaurantId);

}
