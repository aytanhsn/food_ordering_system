package jpaprojects.foodorderingsystem.dtos.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jpaprojects.foodorderingsystem.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequestDTO {
    private String name;
    private String address;
    private String phoneNumber;
    private Double rating;
    private String category; // Category burada String olaraq saxlanacaq
}

