package com.nurali.finalcase.controller;

import com.nurali.finalcase.dto.UserDTO;
import com.nurali.finalcase.entity.Restaurant;
import com.nurali.finalcase.entity.User;
import com.nurali.finalcase.service.UserService;
import com.nurali.finalcase.service.impl.UserServiceImpl;
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
@RequestMapping("/api/users")
@Tag(name = "User Management")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to be deleted") @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user information")
    public ResponseEntity<User> updateUser(
            @PathVariable Long userId, @Valid @RequestBody UserDTO request) {
        User updatedUser = userService.updateUser(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{userId}/recommendations")
    @Operation(summary = "Get recommendations for a user",
            description = "Retrieve recommended restaurants for a given user")
    public ResponseEntity<List<Restaurant>> recommendRestaurants(
            @Parameter(description = "ID of the user") @PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            System.out.println(user);
            List<Restaurant> recommendedRestaurants = userService.recommendRestaurants(user);
            return ResponseEntity.ok(recommendedRestaurants);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
