package com.muscle.user.repository;

import com.muscle.user.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findByGoalBetween(Integer lastBadge, Integer userPoints);
}
