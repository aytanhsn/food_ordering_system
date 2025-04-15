package jpaprojects.foodorderingsystem.convertor;

import jpaprojects.foodorderingsystem.dtos.request.PaymentRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.PaymentResponseDTO;
import jpaprojects.foodorderingsystem.entity.Order;
import jpaprojects.foodorderingsystem.entity.Payment;
import jpaprojects.foodorderingsystem.entity.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentConverter {

    public Payment toEntity(PaymentRequestDTO dto, Order order, User customer) {
        return Payment.builder()
                .order(order)
                .customer(customer)
                .amount(dto.getAmount())
                .paymentMethod(dto.getPaymentMethod())
                .status(jpaprojects.foodorderingsystem.enums.PaymentStatus.PENDING)
                .transactionId(UUID.randomUUID().toString())
                .build();
    }

    public PaymentResponseDTO toDTO(Payment payment) {
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .transactionId(payment.getTransactionId())
                .build();
    }
}
