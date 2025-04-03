package jpaprojects.foodorderingsystem.repository;

import jpaprojects.foodorderingsystem.entity.Order;
import jpaprojects.foodorderingsystem.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByRestaurantId(Long restaurantId);
    List<Order> findByCourierId(Long courierId);
    List<Order> findByStatus(OrderStatus status);
}
