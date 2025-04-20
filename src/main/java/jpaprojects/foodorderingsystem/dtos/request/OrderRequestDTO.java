package jpaprojects.foodorderingsystem.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    @NotNull
    private Long restaurantId;

    @NotNull
    private List<OrderItemRequestDTO> items;
}
