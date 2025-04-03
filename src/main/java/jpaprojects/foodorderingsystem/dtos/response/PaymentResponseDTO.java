package jpaprojects.foodorderingsystem.dtos.response;
import jpaprojects.foodorderingsystem.enums.PaymentMethod;
import jpaprojects.foodorderingsystem.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String transactionId;
}
