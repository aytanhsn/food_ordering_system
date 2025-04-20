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
import org.springframework.security.core.context.SecurityContextHolder;
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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Order> orders;

        if (dto.getTargetType() == ReviewTargetType.COURIER) {
            orders = orderRepository.checkAccessReviewForCourier(customer.getId(), dto.getTargetId());
        } else {
            orders = orderRepository.checkAccessReview(customer.getId(), dto.getTargetId());
        }


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

    public List<ReviewResponseDTO> getReviewsForTarget(String targetType, Long targetId) {
        return reviewRepository.findByTargetTypeAndTargetId(
                ReviewTargetType.valueOf(targetType.toUpperCase()), targetId
        ).stream().map(ReviewConverter::toDTO).collect(Collectors.toList());
    }
}
