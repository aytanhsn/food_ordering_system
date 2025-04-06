package jpaprojects.foodorderingsystem.dtos.request;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    private Long customerId;
    private Long restaurantId;
    private List<OrderItemRequestDTO> items;
}
