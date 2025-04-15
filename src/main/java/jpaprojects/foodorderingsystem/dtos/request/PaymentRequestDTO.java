package jpaprojects.foodorderingsystem.dtos.request;

import jpaprojects.foodorderingsystem.enums.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {
    private Long orderId;
    private Long customerId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
}
