package jpaprojects.foodorderingsystem.convertor;

import jpaprojects.foodorderingsystem.dtos.response.DeliveryResponseDTO;
import jpaprojects.foodorderingsystem.entity.Delivery;

public class DeliveryConverter {
    public static DeliveryResponseDTO toDTO(Delivery delivery) {
        return DeliveryResponseDTO.builder()
                .deliveryId(delivery.getId())
                .orderId(delivery.getOrder().getId())
                .courierEmail(delivery.getCourier().getEmail())
                .status(delivery.getStatus())
                .estimatedTime(delivery.getEstimatedTime())
                .actualDeliveryTime(delivery.getActualDeliveryTime())
                .build();
    }
}
