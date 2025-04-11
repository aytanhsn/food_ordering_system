package jpaprojects.foodorderingsystem.dtos.response;

import jpaprojects.foodorderingsystem.enums.Category;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuItemResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Category category;

    private Long restaurantId;   // Restaurant ID
    private String restaurantName;   // Restaurant adı
}
