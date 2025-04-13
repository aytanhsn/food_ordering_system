package jpaprojects.foodorderingsystem.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDTO {
    @NotNull
    private Long menuItemId;

    @NotNull
    private Integer quantity;
}
