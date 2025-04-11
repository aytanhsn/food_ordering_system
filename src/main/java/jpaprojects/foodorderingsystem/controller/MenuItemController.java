package jpaprojects.foodorderingsystem.controller;

import jakarta.validation.Valid;
import jpaprojects.foodorderingsystem.dtos.request.MenuItemRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.MenuItemResponseDTO;
import jpaprojects.foodorderingsystem.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService service;

    // ✅ Create menu item
    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> addMenuItem(@Valid @RequestBody MenuItemRequestDTO dto) {
        return ResponseEntity.ok(service.addMenuItem(dto));
    }

    // ✅ Update menu item
    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponseDTO> updateMenuItem(@PathVariable Long id,
                                                              @Valid @RequestBody MenuItemRequestDTO dto) {
        return ResponseEntity.ok(service.updateMenuItem(id, dto));
    }

    // ✅ Delete menu item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        service.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get menu items by restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItemResponseDTO>> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(service.getMenuItemsByRestaurant(restaurantId));
    }
}