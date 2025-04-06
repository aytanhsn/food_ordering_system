package jpaprojects.foodorderingsystem.dtos.request;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {
    private Long customerId;
    private Long restaurantId;
    private Long courierId;
    private String comment;
    private Double rating;
}
