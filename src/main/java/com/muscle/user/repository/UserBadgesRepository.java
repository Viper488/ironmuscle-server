package com.muscle.user.repository;

import com.muscle.user.entity.UserBadges;
import com.muscle.user.entity.UserBadgesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBadgesRepository extends JpaRepository<UserBadges, UserBadgesKey> {

    @Modifying
    @Query(value = "DELETE FROM user_badges;", nativeQuery = true)
    void resetBadges();
}
