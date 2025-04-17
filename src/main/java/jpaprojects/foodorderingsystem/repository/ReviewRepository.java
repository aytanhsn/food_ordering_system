package jpaprojects.foodorderingsystem.repository;

import jpaprojects.foodorderingsystem.entity.Review;
import jpaprojects.foodorderingsystem.enums.ReviewTargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTargetTypeAndTargetId(ReviewTargetType targetType, Long targetId);
}
