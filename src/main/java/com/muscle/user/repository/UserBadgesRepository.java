package com.muscle.user.repository;

import com.muscle.user.entity.UserBadges;
import com.muscle.user.entity.UserBadgesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBadgesRepository extends JpaRepository<UserBadges, UserBadgesKey> {
}
