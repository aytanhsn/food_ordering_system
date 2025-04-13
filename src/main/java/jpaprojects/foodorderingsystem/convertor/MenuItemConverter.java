package jpaprojects.foodorderingsystem.convertor;

import jpaprojects.foodorderingsystem.dtos.request.MenuItemRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.MenuItemResponseDTO;
import jpaprojects.foodorderingsystem.entity.MenuItem;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MenuItemConverter {

    public MenuItem toEntity(MenuItemRequestDTO dto, Restaurant restaurant) {
        return MenuItem.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(BigDecimal.valueOf(dto.getPrice())) // Double -> BigDecimal
                .imageUrl(dto.getImageUrl())
                .category(dto.getCategory())
                .restaurant(restaurant)
                .build();
    }

    public MenuItemResponseDTO toDTO(MenuItem menuItem) {
        MenuItemResponseDTO dto = new MenuItemResponseDTO();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice().doubleValue()); // BigDecimal -> Double
        dto.setImageUrl(menuItem.getImageUrl());
        dto.setCategory(menuItem.getCategory());
        dto.setRestaurantId(menuItem.getRestaurant().getId());
        dto.setRestaurantName(menuItem.getRestaurant().getName());
        return dto;
    }
}