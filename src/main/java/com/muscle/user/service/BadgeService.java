package com.muscle.user.service;

import com.muscle.user.entity.Badge;
import com.muscle.user.repository.BadgeRepository;
import com.muscle.user.entity.IronUser;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.response.BadgeResponse;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public List<BadgeResponse> getUserBadges(String header) {
        IronUser user = userRepository.findByUsername(jwtUtil.extractUsername(header))
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return badgeRepository.findByUserId(user.getId()).stream().map(Badge::response).collect(Collectors.toList());
    }
}
