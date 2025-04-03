package jpaprojects.foodorderingsystem.dtos.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Long id;
    private Long customerId;
    private Long restaurantId;
    private Long courierId;
    private String comment;
    private Double rating;
}
