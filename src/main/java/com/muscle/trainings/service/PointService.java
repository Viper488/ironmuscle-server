package com.muscle.trainings.service;

import com.muscle.trainings.entity.Point;
import com.muscle.trainings.repository.PointRepository;
import com.muscle.user.entity.IronUser;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointService {
    private final JwtUtil jwtUtil;
    private final PointRepository pointRepository;

    public void initializePoints(IronUser ironUser) {
        pointRepository.save(Point.builder().ironUser(ironUser).points(0).build());
    }

    public void addPoints(String username, Integer addPoints) {
        Point userPoints = pointRepository.findByIronUserUsername(username)
                .orElseThrow(() -> new IllegalStateException("Entry in points not found"));
        userPoints.setPoints(userPoints.getPoints() + addPoints);

        pointRepository.save(userPoints);
    }

    public Integer getUserPoints(String header) {
        return pointRepository.findByIronUserUsername(jwtUtil.extractUsername(header)).orElseThrow(() -> new IllegalStateException("Entry in points not found")).getPoints();
    }
}
