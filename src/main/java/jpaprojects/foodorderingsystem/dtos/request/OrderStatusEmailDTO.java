package jpaprojects.foodorderingsystem.dtos.request;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusEmailDTO {
    private String customerName;
    private String customerEmail;
    private Long orderId;
    private String orderStatus;
    private String subject;
    private String message;
}
