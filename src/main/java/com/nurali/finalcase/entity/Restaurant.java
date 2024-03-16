package com.nurali.finalcase.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Schema(description = "Restaurant entity")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the restaurant")
    private Long id;

    @Schema(description = "The name of the restaurant")
    private String name;

    @Schema(description = "The address of the restaurant")
    private String address;

    @Schema(description = "The latitude of the restaurant's location")
    private double latitude;

    @Schema(description = "The longitude of the restaurant's location")
    private double longitude;

    @Schema(description = "The score of the restaurant")
    private int score;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews;

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", score=" + score +
                ", reviews=" + reviews +
                '}';
    }

}
