package jpaprojects.foodorderingsystem.service;
import jpaprojects.foodorderingsystem.convertor.MenuItemConverter;
import jpaprojects.foodorderingsystem.dtos.request.MenuItemRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.MenuItemResponseDTO;
import jpaprojects.foodorderingsystem.entity.MenuItem;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import jpaprojects.foodorderingsystem.exception.ResourceNotFoundException;
import jpaprojects.foodorderingsystem.repository.MenuItemRepository;
import jpaprojects.foodorderingsystem.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemConverter converter;

    public MenuItemResponseDTO addMenuItem(MenuItemRequestDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + dto.getRestaurantId()));

        MenuItem menuItem = converter.toEntity(dto, restaurant);
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        return converter.toDTO(savedMenuItem);
    }

    public MenuItemResponseDTO updateMenuItem(Long id, MenuItemRequestDTO dto) {
        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with ID: " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(BigDecimal.valueOf(dto.getPrice())); // Double -> BigDecimal
        existing.setImageUrl(dto.getImageUrl());
        existing.setCategory(dto.getCategory());

        return converter.toDTO(menuItemRepository.save(existing));
    }

    public void deleteMenuItem(Long id) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with ID: " + id));
        menuItemRepository.delete(item);
    }
    @Cacheable(value = "menuItemsByRestaurant", key = "#restaurantId")
    public List<MenuItemResponseDTO> getMenuItemsByRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId));

        return menuItemRepository.findAll().stream()
                .filter(item -> item.getRestaurant().getId().equals(restaurantId))
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }
}