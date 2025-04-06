package jpaprojects.foodorderingsystem.repository;

import jpaprojects.foodorderingsystem.entity.Delivery;
import jpaprojects.foodorderingsystem.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByCourierId(Long courierId);
    List<Delivery> findByStatus(DeliveryStatus status);
}
