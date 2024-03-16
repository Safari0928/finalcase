package com.nurali.finalcase.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Schema(description = "Review entity")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the review")
    private Long id;

    @Schema(description = "The score of the review (1 to 5)")
    private int score;

    @Schema(description = "The text content of the review")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", score=" + score +
                ", text='" + text + '\'' +
                ", restaurantId=" + (restaurant != null ? restaurant.getId() : null) +
                '}';
    }


}
