package com.muscle.user.service;

import com.muscle.user.entity.Badge;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.UserBadges;
import com.muscle.user.entity.UserBadgesKey;
import com.muscle.user.repository.BadgeRepository;
import com.muscle.user.repository.UserBadgesRepository;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBadgesService {

    private final UserBadgesRepository userBadgesRepository;
    private final BadgeRepository badgeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addBadge(String username, int points) {
        IronUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        List<Badge> userBadges = badgeRepository.findByUserId(user.getId());
        List<Badge> pointsBadges = badgeRepository.findByGoalLessThanEqual(points);

        if(pointsBadges.size() > userBadges.size()) {
            Badge newBadge = pointsBadges.get(userBadges.size());
            userBadgesRepository.save(
                    UserBadges.builder()
                            .id(UserBadgesKey.builder()
                                    .userId(user.getId())
                                    .badgeId(newBadge.getId())
                                    .build())
                            .user(user)
                            .badge(newBadge)
                            .build());
        }
    }
}
