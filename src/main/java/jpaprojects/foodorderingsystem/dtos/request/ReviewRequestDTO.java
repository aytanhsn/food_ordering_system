package jpaprojects.foodorderingsystem.dtos.request;

import jpaprojects.foodorderingsystem.enums.ReviewTargetType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {
    private Long customerId;
    private String comment;
    private int rating;
    private ReviewTargetType targetType;
    private Long targetId;
}
