package jpaprojects.foodorderingsystem.convertor;

import jpaprojects.foodorderingsystem.dtos.response.ReviewResponseDTO;
import jpaprojects.foodorderingsystem.entity.Review;

public class ReviewConverter {
    public static ReviewResponseDTO toDTO(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .targetType(review.getTargetType())
                .targetId(review.getTargetId())
                .customerName(review.getCustomer().getFirstName() + " " + review.getCustomer().getLastName())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
