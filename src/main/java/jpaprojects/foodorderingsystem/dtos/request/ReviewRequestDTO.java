package jpaprojects.foodorderingsystem.dtos.request;

import jpaprojects.foodorderingsystem.enums.ReviewTargetType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {
    private String comment;
    private int rating;
    private ReviewTargetType targetType;
    private Long targetId;
}
