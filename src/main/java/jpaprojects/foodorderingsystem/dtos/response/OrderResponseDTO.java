package jpaprojects.foodorderingsystem.dtos.response;

import jpaprojects.foodorderingsystem.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private Long customerId;
    private Long restaurantId;
    private Long courierId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime orderTime;
    private LocalDateTime deliveryTime;
    private List<OrderItemResponseDTO> items;
}

