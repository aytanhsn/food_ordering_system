package jpaprojects.foodorderingsystem.dtos.request;

import jpaprojects.foodorderingsystem.enums.Category;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Category category;
    private Long restaurantId;
}
