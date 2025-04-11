package jpaprojects.foodorderingsystem.mapper;

import jpaprojects.foodorderingsystem.dtos.request.RestaurantRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.RestaurantResponseDTO;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import jpaprojects.foodorderingsystem.enums.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    // Bu metod restoranı DTO-ya çevirir
    RestaurantResponseDTO toRestaurantResponseDTO(Restaurant restaurant);

    // Bu metod restoran siyahısını DTO siyahısına çevirir
    List<RestaurantResponseDTO> toRestaurantResponseDTOList(List<Restaurant> restaurants);

    // Bu metod DTO-yu restoran entity-nə çevirir
    Restaurant toEntity(RestaurantRequestDTO restaurantRequestDTO);

    // Enum String-a çevirmək üçün köməkçi metod
    default String mapCategoryToString(Category category) {
        return category != null ? category.name() : "";
    }

    // String dəyərini Enum-a çevirmək üçün köməkçi metod
    default Category mapStringToCategory(String category) {
        if (category != null && !category.isEmpty()) {
            try {
                return Category.valueOf(category);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }
}
