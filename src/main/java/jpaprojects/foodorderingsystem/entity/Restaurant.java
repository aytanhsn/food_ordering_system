package jpaprojects.foodorderingsystem.entity;

import jakarta.persistence.*;
import jpaprojects.foodorderingsystem.enums.Category;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phoneNumber;
    private Double rating;

    @Enumerated(EnumType.STRING)  // Enum tipini düzgün şəkildə saxlayır
    private Category category; // Category sahəsini buraya əlavə etdim

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<MenuItem> menuItems;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private Set<Order> orders;
}
