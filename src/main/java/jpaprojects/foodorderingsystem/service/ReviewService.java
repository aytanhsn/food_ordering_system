package jpaprojects.foodorderingsystem.service;

import jpaprojects.foodorderingsystem.convertor.ReviewConverter;
import jpaprojects.foodorderingsystem.dtos.request.ReviewRequestDTO;
import jpaprojects.foodorderingsystem.dtos.response.ReviewResponseDTO;
import jpaprojects.foodorderingsystem.entity.*;
import jpaprojects.foodorderingsystem.enums.OrderStatus;
import jpaprojects.foodorderingsystem.enums.PaymentStatus;
import jpaprojects.foodorderingsystem.enums.ReviewTargetType;
import jpaprojects.foodorderingsystem.exception.ResourceNotFoundException;
import jpaprojects.foodorderingsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public ReviewResponseDTO submitReview(ReviewRequestDTO dto) {
        User customer = userRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        List<Order> orders = orderRepository.checkAccessReview(customer.getId(), dto.getTargetId());
//        List<Order> orders = orderRepository.findAll().stream()
//                .filter(order -> order.getCustomer().getId().equals(customer.getId()))
//                .filter(order -> dto.getTargetType() == ReviewTargetType.RESTAURANT ?
//                        order.getRestaurant().getId().equals(dto.getTargetId()) :
//                        order.getCourier() != null && order.getCourier().getId().equals(dto.getTargetId()))
//                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
//                .collect(Collectors.toList());
//
//        boolean hasSuccessfulPayment = orders.stream().anyMatch(order ->
//                paymentRepository.findAll().stream()
//                        .anyMatch(payment ->
//                                payment.getOrder().getId().equals(order.getId())
//                                        && payment.getStatus() == PaymentStatus.SUCCESS
//                        ));

        if (orders.isEmpty()) {
            throw new RuntimeException("Rəy yalnız çatdırılmış və ödənişi tamamlanmış sifarişlərə yazıla bilər.");
        }

        Review review = Review.builder()
                .comment(dto.getComment())
                .rating(dto.getRating())
                .targetType(dto.getTargetType())
                .targetId(dto.getTargetId())
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .build();

        return ReviewConverter.toDTO(reviewRepository.save(review));
    }

    public List<ReviewResponseDTO> getReviewsForTarget(String type, Long id) {
        ReviewTargetType targetType = ReviewTargetType.valueOf(type.toUpperCase());
        return reviewRepository.findByTargetTypeAndTargetId(targetType, id)
                .stream()
                .map(ReviewConverter::toDTO)
                .collect(Collectors.toList());
    }
}
