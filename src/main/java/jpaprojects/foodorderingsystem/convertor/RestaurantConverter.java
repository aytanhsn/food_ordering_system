package jpaprojects.foodorderingsystem.convertor;

import jpaprojects.foodorderingsystem.dtos.request.RestaurantRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.RestaurantResponseDTO;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import jpaprojects.foodorderingsystem.enums.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantConverter {

    public RestaurantResponseDTO toResponseDTO(Restaurant restaurant) {
        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setAddress(restaurant.getAddress());
        dto.setPhoneNumber(restaurant.getPhoneNumber());
        dto.setRating(restaurant.getRating());
        dto.setCategory(restaurant.getCategory() != null ? restaurant.getCategory().name() : null);
        return dto;
    }

    public List<RestaurantResponseDTO> toResponseDTOList(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Restaurant toEntity(RestaurantRequestDTO dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setAddress(dto.getAddress());
        restaurant.setPhoneNumber(dto.getPhoneNumber());
        restaurant.setRating(dto.getRating());

        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            try {
                restaurant.setCategory(Category.valueOf(dto.getCategory()));
            } catch (IllegalArgumentException e) {
                restaurant.setCategory(null);
            }
        }
        return restaurant;
    }
}
