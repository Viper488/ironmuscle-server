package com.muscle.trainings.service;

import com.muscle.trainings.entity.Point;
import com.muscle.trainings.repository.PointRepository;
import com.muscle.trainings.responses.PointResponse;
import com.muscle.trainings.responses.RankingResponse;
import com.muscle.user.entity.IronUser;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointService {
    private final JwtUtil jwtUtil;
    private final PointRepository pointRepository;

    public void initializePoints(IronUser ironUser) {
        pointRepository.save(Point.builder().user(ironUser).points(0).build());
    }

    public void addPoints(String username, Integer addPoints) {
        Point userPoints = pointRepository.findByUserUsername(username)
                .orElseThrow(() -> new IllegalStateException("Entry in points not found"));
        userPoints.setPoints(userPoints.getPoints() + addPoints);

        pointRepository.save(userPoints);
    }

    @Transactional
    public Page<Tuple> getPaginatedRanking(Pageable pageable) {
        return pointRepository.getRanking(pageable);
    }

    @Transactional
    public RankingResponse getUserRank(String header) {
        Tuple userRank = pointRepository.getRankByUsername(jwtUtil.extractUsername(header)).orElseThrow(()->new IllegalStateException("Ranking entry not found"));

        return RankingResponse.builder()
                .rank(userRank.get(0, BigInteger.class).longValue())
                .username(userRank.get(1, String.class))
                .icon(userRank.get(2, byte[].class))
                .points(userRank.get(3, Integer.class))
                .build();
    }
}
