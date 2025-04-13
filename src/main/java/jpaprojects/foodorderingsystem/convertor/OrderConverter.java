package jpaprojects.foodorderingsystem.convertor;

import jpaprojects.foodorderingsystem.dtos.response.OrderItemResponseDTO;
import jpaprojects.foodorderingsystem.dtos.response.OrderResponseDTO;
import jpaprojects.foodorderingsystem.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {

    public static OrderResponseDTO toDTO(Order order) {
        List<OrderItemResponseDTO> items = order.getOrderItems().stream().map(item -> {
            return OrderItemResponseDTO.builder()
                    .id(item.getId())
                    .menuItemId(item.getMenuItem().getId())
                    .menuItemName(item.getMenuItem().getName())
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .build();
        }).collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .restaurantId(order.getRestaurant().getId())
                .courierId(order.getCourier() != null ? order.getCourier().getId() : null)
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .orderTime(order.getOrderTime())
                .deliveryTime(order.getDeliveryTime())
                .items(items)
                .build();
    }
}