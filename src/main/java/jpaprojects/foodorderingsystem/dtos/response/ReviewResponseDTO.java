package jpaprojects.foodorderingsystem.dtos.response;

import jpaprojects.foodorderingsystem.enums.ReviewTargetType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Long id;
    private String comment;
    private int rating;
    private ReviewTargetType targetType;
    private Long targetId;
    private String customerEmail;
    private LocalDateTime createdAt;
}
