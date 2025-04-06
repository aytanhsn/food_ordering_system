package jpaprojects.foodorderingsystem.dtos.response;import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {
    private Long id;
    private Long menuItemId;
    private Integer quantity;
    private BigDecimal price;
}
