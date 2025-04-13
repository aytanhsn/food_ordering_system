package jpaprojects.foodorderingsystem.convertor;

import jpaprojects.foodorderingsystem.dtos.response.OrderItemResponseDTO;
import jpaprojects.foodorderingsystem.entity.OrderItem;

public class OrderItemConverter {

    public static OrderItemResponseDTO toDTO(OrderItem item) {
        return OrderItemResponseDTO.builder()
                .id(item.getId())
                .menuItemId(item.getMenuItem().getId()) // MenuItem-in ID-si çəkilir
                .menuItemName(item.getMenuItem().getName()) // MenuItem-in adı çəkilir
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }
}
