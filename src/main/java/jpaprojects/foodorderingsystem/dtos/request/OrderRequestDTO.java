package jpaprojects.foodorderingsystem.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    @NotNull
    private Long customerId;

    @NotNull
    private Long restaurantId;

    @NotNull
    private List<OrderItemRequestDTO> items;
}// menyu məhsulları və miqdar
