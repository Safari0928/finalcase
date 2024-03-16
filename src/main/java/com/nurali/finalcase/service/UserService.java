package com.nurali.finalcase.service;

import com.nurali.finalcase.dto.UserDTO;
import com.nurali.finalcase.entity.Restaurant;
import com.nurali.finalcase.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO);

    void deleteUser(Long userId);

    User updateUser(Long userId, UserDTO request);

    User getUserById(Long userId);

    List<Restaurant> recommendRestaurants(User user);
}
