package jpaprojects.foodorderingsystem.mapper;

import jpaprojects.foodorderingsystem.dtos.request.RestaurantRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.RestaurantResponseDTO;
import jpaprojects.foodorderingsystem.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant toEntity(RestaurantRequestDTO dto);

    RestaurantResponseDTO toDto(Restaurant entity);
}
