package com.nurali.finalcase.service.impl;

import com.nurali.finalcase.dto.UserDTO;
import com.nurali.finalcase.entity.Restaurant;
import com.nurali.finalcase.entity.User;
import com.nurali.finalcase.repository.RestaurantRepository;
import com.nurali.finalcase.repository.UserRepository;
import com.nurali.finalcase.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }
    @Override
    public User createUser(UserDTO userDTO) {
        try {
            if (userDTO == null) {
                throw new IllegalArgumentException("UserDTO cannot be null");
            }

            User user = new User();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setLatitude(userDTO.getLatitude());
            user.setLongitude(userDTO.getLongitude());
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error occurred while creating user: {}", e.getMessage());
            throw new RuntimeException("Error occurred while creating user", e);
        }
    }
    @Override
    public void deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            log.error("Error occurred while deleting user with ID {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error occurred while deleting user with ID " + userId, e);
        }
    }
    @Override
    public User updateUser(Long userId, UserDTO request) {
        try {
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

            // Update user information if provided
            if (request.getFirstName() != null) {
                existingUser.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null) {
                existingUser.setLastName(request.getLastName());
            }
            if (request.getLatitude() != null) {
                existingUser.setLatitude(request.getLatitude());
            }
            if (request.getLongitude() != null) {
                existingUser.setLongitude(request.getLongitude());
            }
            return userRepository.save(existingUser);

        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating user with ID {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error occurred while updating user with ID " + userId, e);
        }
    }
    @Override
    public User getUserById(Long userId) {
        try {
            return userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while retrieving user with ID {}: {}", userId, e.getMessage());
            throw new RuntimeException("Error occurred while retrieving user with ID " + userId, e);
        }
    }

    // Kullanıcıya öneri sunan bir API
    // Bu API, kullanıcının konumunu ve restoranın puanını göz önünde bulundurarak 3 restoran önerisi sunar
    @Override
    public List<Restaurant> recommendRestaurants(User user) {

      try {
          // Kullanıcının konumunu al
          double userLatitude = user.getLatitude();
          double userLongitude = user.getLongitude();

          // Tüm restoranları veritabanından al
          List<Restaurant> allRestaurants = restaurantRepository.findAllWithReviews();

          // Kullanıcıya en yakın restoration bul
          List<Restaurant> nearbyRestaurants = allRestaurants.stream()
                  .filter(restaurant -> calculateDistance(userLatitude, userLongitude, restaurant.getLatitude(), restaurant.getLongitude()) <= 10.0)
                  .collect(Collectors.toList());

          // Kullanıcının lokasyonuna göre restoranlara puan ver ve sırala
          nearbyRestaurants.sort(Comparator.comparingDouble(restaurant -> calculateScoreAndDistanceWeight(user, restaurant)));

          // En iyi 3 restoranı al
          return nearbyRestaurants.stream().limit(3).collect(Collectors.toList());
      }catch(Exception e) {
          log.error("Error occurred while recommending restaurants for user {}: {}", user.getId(), e.getMessage());
          throw new RuntimeException("Error occurred while recommending restaurants for user " + user.getId(), e);
      }
    }

    // Kullanıcının lokasyonuna ve restoranın puanına göre ağırlıklı skoru hesapla
    private double calculateScoreAndDistanceWeight(User user, Restaurant restaurant) {
        try {
            double distanceWeight = 1 - (calculateDistance(user.getLatitude(), user.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude()) / 10.0);
            double scoreWeight = restaurant.getScore() * 0.7;
            double distanceScoreWeight = distanceWeight * 0.3;
            return scoreWeight + distanceScoreWeight;
        } catch (Exception e) {
            log.error("Error occurred while calculating score and distance weight for user {} and restaurant {}: {}", user.getId(), restaurant.getId(), e.getMessage());
            throw new RuntimeException("Error occurred while calculating score and distance weight for user " + user.getId() + " and restaurant " + restaurant.getId(), e);
        }
    }

    // İki konum arasındaki mesafeyi hesapla
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine formülü kullanarak mesafeyi hesapla
        double radius = 6371; // Dünya'nın yarıçapı (km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radius * c;
    }
}
