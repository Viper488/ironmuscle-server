package com.muscle.user.repository;

import com.muscle.user.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    @Query(value = "SELECT * FROM badge b WHERE b.id IN (SELECT badge_id FROM user_badges ub WHERE ub.user_id = ?1)", nativeQuery = true)
    List<Badge> findByUserId(Long id);
}
