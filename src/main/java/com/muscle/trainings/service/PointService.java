package com.muscle.trainings.service;

import com.muscle.trainings.entity.Point;
import com.muscle.trainings.entity.Ranking;
import com.muscle.trainings.repository.PointRepository;
import com.muscle.trainings.repository.RankingRepository;
import com.muscle.trainings.responses.PointResponse;
import com.muscle.user.entity.IronUser;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointService {
    private final JwtUtil jwtUtil;
    private final PointRepository pointRepository;
    private final RankingRepository rankingRepository;

    public void initializePoints(IronUser ironUser) {
        pointRepository.save(Point.builder().user(ironUser).points(0).build());
    }

    public void addPoints(String username, Integer addPoints) {
        Point userPoints = pointRepository.findByUserUsername(username)
                .orElseThrow(() -> new IllegalStateException("Entry in points not found"));
        userPoints.setPoints(userPoints.getPoints() + addPoints);

        pointRepository.save(userPoints);
    }

    public PointResponse getUserPoints(String header) {
        return pointRepository.findByUserUsername(jwtUtil.extractUsername(header)).orElseThrow(() -> new IllegalStateException("Entry in points not found")).response();
    }
}
