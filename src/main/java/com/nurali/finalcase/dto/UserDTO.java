package com.nurali.finalcase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

@Data
@Schema(description = "User DTO")
public class UserDTO {


    @NotBlank(message = "First name must not be blank")
    @Schema(description = "The first name of the user")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Schema(description = "The last name of the user")
    private String lastName;

    @NotNull(message = "Latitude must not be null")
    @Schema(description = "Latitude of the user's location")
    private Double latitude;

    @NotNull(message = "Longitude must not be null")
    @Schema(description = "Longitude of the user's location")
    private Double longitude;

    @Null  // Optional field
    private String optionalField;
}
