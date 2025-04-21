package jpaprojects.foodorderingsystem.dtos.request;

import lombok.*;
import org.hibernate.annotations.SecondaryRow;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfirmationEmailDTO {
    private String customerEmail;
    private String customerName;
    private Long orderId;
    private Double paidAmount;
}
