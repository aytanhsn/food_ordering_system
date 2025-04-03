package jpaprojects.foodorderingsystem.mapper;

import jpaprojects.foodorderingsystem.dtos.request.OrderRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.OrderResponseDTO;
import jpaprojects.foodorderingsystem.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toEntity(OrderRequestDTO dto);

    OrderResponseDTO toDto(Order entity);
}
