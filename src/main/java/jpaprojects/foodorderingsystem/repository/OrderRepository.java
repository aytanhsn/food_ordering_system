package jpaprojects.foodorderingsystem.repository;

import jpaprojects.foodorderingsystem.dtos.request.ReviewRequestDTO;
import jpaprojects.foodorderingsystem.entity.Order;
import jpaprojects.foodorderingsystem.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
    select o from Order o 
        where o.customer.id = :customerId 
        and o.delivery.status = 'DELIVERED'
        and o.restaurant.id = :targetId
        and o.payment.status = 'SUCCESS'
    """)
    List<Order> checkAccessReview(Long customerId , Long targetId);

    @Query("""
    select o from Order o 
        where o.customer.id = :customerId 
        and o.delivery.status = 'DELIVERED'
        and o.courier.id = :targetId
        and o.payment.status = 'SUCCESS'
""")
    List<Order> checkAccessReviewForCourier(Long customerId , Long targetId);


}
