package jpaprojects.foodorderingsystem.service;

import jpaprojects.foodorderingsystem.convertor.RestaurantConverter;
import jpaprojects.foodorderingsystem.dtos.request.RestaurantRequestDTO;
import jpaprojects.foodorderingsystem.dtos.request.RestaurantUpdateRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.RestaurantResponseDTO;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import jpaprojects.foodorderingsystem.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import jpaprojects.foodorderingsystem.dtos.request.RestaurantRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.RestaurantResponseDTO;
import jpaprojects.foodorderingsystem.entity.Restaurant;

import jpaprojects.foodorderingsystem.repository.RestaurantRepository;
import jpaprojects.foodorderingsystem.enums.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantConverter restaurantConverter;
    @Cacheable(value = "restaurants", key = "#root.methodName")
    public List<RestaurantResponseDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantConverter.toResponseDTOList(restaurants);
    }

    public String addRestaurant(RestaurantRequestDTO restaurantRequestDTO) {
        if (restaurantRequestDTO.getName() == null || restaurantRequestDTO.getName().isEmpty()) {
            return "Restoran adı boş ola bilməz!";
        }
        if (restaurantRequestDTO.getCategory() == null || restaurantRequestDTO.getCategory().isEmpty()) {
            return "Restoranın kateqoriyası boş ola bilməz!";
        }

        try {
            Category.valueOf(restaurantRequestDTO.getCategory());
        } catch (IllegalArgumentException e) {
            return "Yanlış kateqoriya dəyəri!";
        }

        Restaurant restaurant = restaurantConverter.toEntity(restaurantRequestDTO);
        restaurantRepository.save(restaurant);
        return "Restoran uğurla əlavə edildi!";
    }

    public String updateRestaurant(Long restaurantId, RestaurantUpdateRequestDTO restaurantUpdateRequestDTO) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (existingRestaurant == null) {
            return "Restoran tapılmadı!";
        }

        if (restaurantUpdateRequestDTO.getName() != null) {
            existingRestaurant.setName(restaurantUpdateRequestDTO.getName());
        }
        if (restaurantUpdateRequestDTO.getAddress() != null) {
            existingRestaurant.setAddress(restaurantUpdateRequestDTO.getAddress());
        }
        if (restaurantUpdateRequestDTO.getPhoneNumber() != null) {
            existingRestaurant.setPhoneNumber(restaurantUpdateRequestDTO.getPhoneNumber());
        }
        if (restaurantUpdateRequestDTO.getRating() != null) {
            existingRestaurant.setRating(restaurantUpdateRequestDTO.getRating());
        }

        restaurantRepository.save(existingRestaurant);
        return "Restoran uğurla yeniləndi!";
    }
}

