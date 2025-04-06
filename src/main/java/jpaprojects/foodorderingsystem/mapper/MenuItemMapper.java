package jpaprojects.foodorderingsystem.mapper;

import jpaprojects.foodorderingsystem.dtos.request.MenuItemRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.MenuItemResponseDTO;
import jpaprojects.foodorderingsystem.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MenuItemMapper {
    MenuItemMapper INSTANCE = Mappers.getMapper(MenuItemMapper.class);

    MenuItem toEntity(MenuItemRequestDTO dto);

    MenuItemResponseDTO toDto(MenuItem entity);
}
