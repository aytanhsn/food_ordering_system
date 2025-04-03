package jpaprojects.foodorderingsystem.mapper;

import jpaprojects.foodorderingsystem.dtos.request.OrderItemRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.OrderItemResponseDTO;
import jpaprojects.foodorderingsystem.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItem toEntity(OrderItemRequestDTO dto);

    OrderItemResponseDTO toDto(OrderItem entity);
}
