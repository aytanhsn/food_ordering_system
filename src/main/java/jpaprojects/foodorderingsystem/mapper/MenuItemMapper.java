package jpaprojects.foodorderingsystem.mapper;
import jpaprojects.foodorderingsystem.dtos.request.MenuItemRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.MenuItemResponseDTO;
import jpaprojects.foodorderingsystem.entity.MenuItem;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    // MenuItemRequestDTO-nun restaurantId-sini restaurant obyektinə çeviririk
    @Mapping(source = "restaurantId", target = "restaurant.id")
    MenuItem toEntity(MenuItemRequestDTO dto, @Context Restaurant restaurant);

    // MenuItem entity-ni MenuItemResponseDTO-ya çevirmək
    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "restaurant.name", target = "restaurantName")
    MenuItemResponseDTO toDTO(MenuItem menuItem);
}





