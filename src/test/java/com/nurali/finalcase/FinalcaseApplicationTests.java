package com.nurali.finalcase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.nurali.finalcase.dto.UserDTO;
import com.nurali.finalcase.entity.User;
import com.nurali.finalcase.service.UserService;
import com.nurali.finalcase.controller.UserController;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.boot.test.context.SpringBootTest;


import jakarta.validation.Validator;


import java.util.Set;


@SpringBootTest
class FinalcaseApplicationTests {

    @Autowired
    @Mock
    private UserService userService;

    @Autowired
    private UserController userController;

    @Autowired
    private Validator validator;

    @Test
    void testCreateUser() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setLatitude(37.7749);
        userDTO.setLongitude(-122.4194);

        // Validate UserDTO
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        assertEquals(0, violations.size(), "UserDTO is not valid");

        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setFirstName("John");
        createdUser.setLastName("Doe");
        createdUser.setLatitude(37.7749);
        createdUser.setLongitude(-122.4194);

        // Mock the UserService's createUser method
        doReturn(createdUser).when(userService).createUser(any(UserDTO.class));

        // When
        ResponseEntity<User> response = userController.createUser(userDTO);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdUser, response.getBody()); // Check if the returned user matches the expected user
        verify(userService, times(1)).createUser(any(UserDTO.class)); // Verify that the UserService's createUser method was called
    }

    @Test
    void testUserServiceMock() {
        assertNotNull(userService);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setLatitude(37.7749);
        userDTO.setLongitude(-122.4194);

        User createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
    }
}

