package com.nurali.finalcase.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "AppUser")
@Schema(description = "User entity")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the user")
    private Long id;

    @Schema(description = "The first name of the user")
    private String firstName;

    @Schema(description = "The last name of the user")
    private String lastName;

    @Schema(description = "The latitude of the user's location")
    private double latitude;

    @Schema(description = "The longitude of the user's location")
    private double longitude;
}
