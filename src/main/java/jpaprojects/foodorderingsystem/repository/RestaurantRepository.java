package jpaprojects.foodorderingsystem.repository;

import jpaprojects.foodorderingsystem.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    // Bu repository sadəcə `findAll` istifadə edirik, çünki müştərilərə bütün restoranları göstərəcəyik
}
