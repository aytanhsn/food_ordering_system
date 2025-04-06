package jpaprojects.foodorderingsystem.dtos.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryRequestDTO {
    private Long orderId;
    private Long courierId;
}
