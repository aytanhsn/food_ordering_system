package jpaprojects.foodorderingsystem.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jpaprojects.foodorderingsystem.enums.Category;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
public class MenuItemRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.1", message = "Price must be at least 0.1")
    private Double price;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;  // Yalnız restaurantId göndərilir
}
