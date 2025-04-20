package jpaprojects.foodorderingsystem.entity;

import jakarta.persistence.*;
import jpaprojects.foodorderingsystem.enums.ReviewTargetType;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private int rating;

    @Enumerated(EnumType.STRING)
    private ReviewTargetType targetType;

    private Long targetId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    private LocalDateTime createdAt;
}
