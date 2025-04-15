package jpaprojects.foodorderingsystem.repository;

import jpaprojects.foodorderingsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
