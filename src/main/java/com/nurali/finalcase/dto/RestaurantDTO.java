package com.nurali.finalcase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Restaurant DTO")
public class RestaurantDTO {

    @NotBlank(message = "Restaurant name must not be blank")
    @Schema(description = "The name of the restaurant")
    private String name;

    @NotBlank(message = "Restaurant address must not be blank")
    @Schema(description = "The address of the restaurant")
    private String address;

    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 5, message = "Score must be at most 5")
    @Schema(description = "The score of the restaurant (between 0 and 5)")
    private int score;

    @Schema(description = "The latitude of the restaurant's location")
    private double latitude;

    @Schema(description = "The longitude of the restaurant's location")
    private double longitude;

    @Schema(description = "List of reviews for the restaurant")
    private List<ReviewDTO> reviews; // 添加一个字段用于存储Review列表

}
