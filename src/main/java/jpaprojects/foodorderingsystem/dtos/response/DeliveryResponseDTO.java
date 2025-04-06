package jpaprojects.foodorderingsystem.dtos.response;
import jpaprojects.foodorderingsystem.enums.DeliveryStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryResponseDTO {
    private Long id;
    private Long orderId;
    private Long courierId;
    private DeliveryStatus status;
    private LocalDateTime estimatedTime;
    private LocalDateTime actualDeliveryTime;
}
