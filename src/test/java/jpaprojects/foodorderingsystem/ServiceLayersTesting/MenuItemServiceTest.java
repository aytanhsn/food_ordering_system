package jpaprojects.foodorderingsystem.ServiceLayersTesting;

import jpaprojects.foodorderingsystem.convertor.MenuItemConverter;
import jpaprojects.foodorderingsystem.dtos.request.MenuItemRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.MenuItemResponseDTO;
import jpaprojects.foodorderingsystem.entity.MenuItem;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import jpaprojects.foodorderingsystem.enums.Category;
import jpaprojects.foodorderingsystem.exception.ResourceNotFoundException;
import jpaprojects.foodorderingsystem.repository.MenuItemRepository;
import jpaprojects.foodorderingsystem.repository.RestaurantRepository;
import jpaprojects.foodorderingsystem.service.MenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemConverter converter;

    @InjectMocks
    private MenuItemService menuItemService;

    private AutoCloseable closeable;

    private Restaurant restaurant;
    private MenuItem menuItem;
    private MenuItemRequestDTO requestDTO;
    private MenuItemResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        restaurant = Restaurant.builder()
                .id(1L)
                .name("Test Restaurant")
                .build();

        menuItem = MenuItem.builder()
                .id(1L)
                .name("Pizza")
                .description("Delicious")
                .price(BigDecimal.valueOf(15.0))
                .imageUrl("http://image.com")
                .category(Category.FOOD)
                .restaurant(restaurant)
                .build();

        requestDTO = new MenuItemRequestDTO();
        requestDTO.setName("Pizza");
        requestDTO.setDescription("Delicious");
        requestDTO.setPrice(15.0);
        requestDTO.setImageUrl("http://image.com");
        requestDTO.setCategory(Category.FOOD);
        requestDTO.setRestaurantId(1L);

        responseDTO = new MenuItemResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Pizza");
        responseDTO.setDescription("Delicious");
        responseDTO.setPrice(15.0);
        responseDTO.setImageUrl("http://image.com");
        responseDTO.setCategory(Category.FOOD);
        responseDTO.setRestaurantId(1L);
        responseDTO.setRestaurantName("Test Restaurant");
    }

    @Test
    void testAddMenuItem() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(converter.toEntity(requestDTO, restaurant)).thenReturn(menuItem);
        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);
        when(converter.toDTO(menuItem)).thenReturn(responseDTO);

        MenuItemResponseDTO result = menuItemService.addMenuItem(requestDTO);

        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        verify(menuItemRepository).save(menuItem);
    }

    @Test
    void testAddMenuItem_RestaurantNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuItemService.addMenuItem(requestDTO));
    }

    @Test
    void testUpdateMenuItem() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);
        when(converter.toDTO(any(MenuItem.class))).thenReturn(responseDTO);

        MenuItemResponseDTO result = menuItemService.updateMenuItem(1L, requestDTO);

        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        verify(menuItemRepository).save(menuItem);
    }

    @Test
    void testUpdateMenuItem_NotFound() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuItemService.updateMenuItem(1L, requestDTO));
    }

    @Test
    void testDeleteMenuItem() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

        menuItemService.deleteMenuItem(1L);

        verify(menuItemRepository).delete(menuItem);
    }

    @Test
    void testDeleteMenuItem_NotFound() {
        when(menuItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuItemService.deleteMenuItem(1L));
    }

    @Test
    void testGetMenuItemsByRestaurant() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.findAll()).thenReturn(List.of(menuItem));
        when(converter.toDTO(menuItem)).thenReturn(responseDTO);

        List<MenuItemResponseDTO> result = menuItemService.getMenuItemsByRestaurant(1L);

        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getName());
    }

    @Test
    void testGetMenuItemsByRestaurant_NotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuItemService.getMenuItemsByRestaurant(1L));
    }
}

