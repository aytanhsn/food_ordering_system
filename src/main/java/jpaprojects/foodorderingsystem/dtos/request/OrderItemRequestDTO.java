package jpaprojects.foodorderingsystem.dtos.request;import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDTO {
    private Long menuItemId;
    private Integer quantity;
}
