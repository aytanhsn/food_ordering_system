package jpaprojects.foodorderingsystem.service;

import jpaprojects.foodorderingsystem.convertor.PaymentConverter;
import jpaprojects.foodorderingsystem.dtos.request.PaymentRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.PaymentResponseDTO;
import jpaprojects.foodorderingsystem.entity.Order;
import jpaprojects.foodorderingsystem.entity.Payment;
import jpaprojects.foodorderingsystem.entity.User;
import jpaprojects.foodorderingsystem.enums.PaymentStatus;
import jpaprojects.foodorderingsystem.exception.ResourceNotFoundException;
import jpaprojects.foodorderingsystem.repository.OrderRepository;
import jpaprojects.foodorderingsystem.repository.PaymentRepository;
import jpaprojects.foodorderingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentConverter converter;

    public PaymentResponseDTO makePayment(PaymentRequestDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        Payment payment = converter.toEntity(dto, order, customer);

        if (dto.getAmount().compareTo(order.getTotalAmount()) < 0) {
            payment.setStatus(PaymentStatus.FAILED);
        } else {
            payment.setStatus(PaymentStatus.SUCCESS);
        }

        return converter.toDTO(paymentRepository.save(payment));
    }

    public PaymentResponseDTO getPaymentStatus(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        return converter.toDTO(payment);
    }
}
