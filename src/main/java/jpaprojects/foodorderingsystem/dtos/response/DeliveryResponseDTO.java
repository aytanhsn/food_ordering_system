package jpaprojects.foodorderingsystem.dtos.response;

import jpaprojects.foodorderingsystem.enums.DeliveryStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDTO {
    private Long deliveryId;
    private Long orderId;
    private String courierEmail;
    private DeliveryStatus status;
    private LocalDateTime estimatedTime;
    private LocalDateTime actualDeliveryTime;
}
