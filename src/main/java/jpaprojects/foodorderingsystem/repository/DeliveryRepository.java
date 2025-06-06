package jpaprojects.foodorderingsystem.repository;

import jpaprojects.foodorderingsystem.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByCourierEmail(String email);
}
