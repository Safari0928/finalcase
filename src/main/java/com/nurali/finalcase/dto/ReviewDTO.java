package com.nurali.finalcase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Review DTO")
public class ReviewDTO {

    @Schema(description = "The unique identifier of the review")
    private Long id;

    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 5, message = "Score must be at most 5")
    @Schema(description = "The score of the review (1 to 5)")
    private int score;

    @NotBlank(message = "Review text must not be blank")
    @Schema(description = "The text content of the review")
    private String text;


    @Schema(description = "The name of the restaurant")
    private String restaurantName; // 添加一个字段用于存储Restaurant的名称
}
