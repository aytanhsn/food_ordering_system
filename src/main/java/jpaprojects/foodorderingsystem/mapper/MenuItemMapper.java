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
    MenuItemResponseDTO toDTO(MenuItem entity);

    // MenuItemRequestDTO içərisindəki restaurantId ilə əlaqəli Restaurant tapıb MenuItem yaratmaq
    @Mapping(target = "restaurant", source = "restaurant")
    MenuItem toEntity(MenuItemRequestDTO dto, Restaurant restaurant);
}


