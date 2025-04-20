package jpaprojects.foodorderingsystem.controller;

import jpaprojects.foodorderingsystem.dtos.request.RestaurantRequestDTO;
import jpaprojects.foodorderingsystem.dtos.request.RestaurantUpdateRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.RestaurantResponseDTO;
import jpaprojects.foodorderingsystem.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/")
    public List<RestaurantResponseDTO> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<String> addRestaurant(@RequestBody RestaurantRequestDTO restaurantRequestDTO) {
        String response = restaurantService.addRestaurant(restaurantRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRestaurant(@PathVariable Long id,
                                                   @RequestBody RestaurantUpdateRequestDTO restaurantUpdateRequestDTO) {
        String response = restaurantService.updateRestaurant(id, restaurantUpdateRequestDTO);
        return ResponseEntity.ok(response);
    }
}
