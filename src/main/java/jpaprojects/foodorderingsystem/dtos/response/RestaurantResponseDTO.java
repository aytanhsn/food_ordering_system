package jpaprojects.foodorderingsystem.dtos.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jpaprojects.foodorderingsystem.enums.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private Double rating;
    private String category; // Category burada String olaraq saxlanacaq
}
