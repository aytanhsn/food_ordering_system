package jpaprojects.foodorderingsystem.service;

import jpaprojects.foodorderingsystem.dtos.request.MenuItemRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.MenuItemResponseDTO;
import jpaprojects.foodorderingsystem.entity.MenuItem;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import jpaprojects.foodorderingsystem.exception.ResourceNotFoundException;
import jpaprojects.foodorderingsystem.mapper.MenuItemMapper;
import jpaprojects.foodorderingsystem.repository.MenuItemRepository;
import jpaprojects.foodorderingsystem.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemMapper mapper;

    public MenuItemResponseDTO addMenuItem(MenuItemRequestDTO dto) {
        // Restoranı restaurantId ilə tapırıq
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + dto.getRestaurantId()));

        // MenuItem entity-ni yaradıb əlaqələndiririk
        MenuItem menuItem = mapper.toEntity(dto, restaurant);

        // MenuItem-ı bazaya qeyd edirik
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        // ResponseDTO-ya restoranın adı və ID'sini əlavə edirik
        MenuItemResponseDTO responseDTO = mapper.toDTO(savedMenuItem);
        responseDTO.setRestaurantId(restaurant.getId());  // Restaurant ID
        responseDTO.setRestaurantName(restaurant.getName());  // Restaurant adı

        return responseDTO;
    }

    public MenuItemResponseDTO updateMenuItem(Long id, MenuItemRequestDTO dto) {
        // Mövcud MenuItem tapılır
        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with ID: " + id));

        // MenuItem yenilənir
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setImageUrl(dto.getImageUrl());
        existing.setCategory(dto.getCategory());

        // Yenilənmiş MenuItem response olaraq qaytarılır
        return mapper.toDTO(menuItemRepository.save(existing));
    }

    public void deleteMenuItem(Long id) {
        // MenuItem tapılır və silinir
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with ID: " + id));
        menuItemRepository.delete(item);
    }

    public List<MenuItemResponseDTO> getMenuItemsByRestaurant(Long restaurantId) {
        // Restaurant tapılır
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with ID: " + restaurantId));

        // Menü items restaurant-a görə filterlənir və response olaraq dövr edilir
        return menuItemRepository.findAll().stream()
                .filter(item -> item.getRestaurant().getId().equals(restaurantId))
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}