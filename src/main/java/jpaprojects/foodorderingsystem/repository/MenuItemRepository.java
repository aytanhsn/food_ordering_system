package jpaprojects.foodorderingsystem.repository;

import jpaprojects.foodorderingsystem.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findAllByRestaurantId(Long restaurantId);
}
