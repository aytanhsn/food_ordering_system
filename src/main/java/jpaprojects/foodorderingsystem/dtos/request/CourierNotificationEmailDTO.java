package jpaprojects.foodorderingsystem.dtos.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourierNotificationEmailDTO {
    private String courierEmail;
    private String courierName;
    private Long orderId;
    private String deliveryAddress;
}
