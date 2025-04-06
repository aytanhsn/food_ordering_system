package jpaprojects.foodorderingsystem.entity;

import jakarta.persistence.*;
import jpaprojects.foodorderingsystem.enums.DeliveryStatus;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "courier_id", nullable = false)
    private User courier;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private LocalDateTime estimatedTime;
    private LocalDateTime actualDeliveryTime;
}
