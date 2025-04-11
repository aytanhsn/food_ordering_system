package jpaprojects.foodorderingsystem.entity;

import jakarta.persistence.*;
import jpaprojects.foodorderingsystem.enums.Category;
import lombok.*;

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
    private Double price;
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "restaurant_id") // restaurant_id-nin uyğunluğu
    private Restaurant restaurant; // Bu əlaqə doğru qurulmalıdır
}
