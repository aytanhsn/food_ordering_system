package jpaprojects.foodorderingsystem.service;

import jpaprojects.foodorderingsystem.dtos.request.RestaurantRequestDTO;
import jpaprojects.foodorderingsystem.dtos.request.RestaurantUpdateRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.RestaurantResponseDTO;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import jpaprojects.foodorderingsystem.mapper.RestaurantMapper;
import jpaprojects.foodorderingsystem.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import jpaprojects.foodorderingsystem.dtos.request.RestaurantRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.RestaurantResponseDTO;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import jpaprojects.foodorderingsystem.mapper.RestaurantMapper;
import jpaprojects.foodorderingsystem.repository.RestaurantRepository;
import jpaprojects.foodorderingsystem.enums.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public List<RestaurantResponseDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantMapper.toRestaurantResponseDTOList(restaurants);
    }

    public String addRestaurant(RestaurantRequestDTO restaurantRequestDTO) {
        if (restaurantRequestDTO.getName() == null || restaurantRequestDTO.getName().isEmpty()) {
            return "Restoran adı boş ola bilməz!";
        }
        if (restaurantRequestDTO.getCategory() == null || restaurantRequestDTO.getCategory().isEmpty()) {
            return "Restoranın kateqoriyası boş ola bilməz!";
        }

        // Category dəyərinin düzgünlüyünü yoxlamaq
        try {
            Category.valueOf(restaurantRequestDTO.getCategory()); // burada category doğru olmalıdır
        } catch (IllegalArgumentException e) {
            return "Yanlış kateqoriya dəyəri!";
        }

        // Restaurant-ı entity-yə çeviririk və saxlamağa çalışırıq
        Restaurant restaurant = restaurantMapper.toEntity(restaurantRequestDTO);
        restaurantRepository.save(restaurant);
        return "Restoran uğurla əlavə edildi!";
    }
    public String updateRestaurant(Long restaurantId, RestaurantUpdateRequestDTO restaurantUpdateRequestDTO) {
        Restaurant existingRestaurant = restaurantRepository.findById(restaurantId).orElse(null);

        if (existingRestaurant == null) {
            return "Restoran tapılmadı!";
        }

        // Restoranın məlumatlarını yeniləyirik
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

        restaurantRepository.save(existingRestaurant); // Dəyişiklikləri yadda saxlayırıq
        return "Restoran uğurla yeniləndi!";
    }
}

