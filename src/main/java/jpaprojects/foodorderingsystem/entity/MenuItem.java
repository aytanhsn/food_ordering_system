package jpaprojects.foodorderingsystem.entity;

import jakarta.persistence.*;
import jpaprojects.foodorderingsystem.enums.Category;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Category category; // FOOD, DRINK, DESSERT

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
