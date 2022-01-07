package com.muscle.trainings.service;

import com.muscle.trainings.entity.Point;
import com.muscle.trainings.repository.PointRepository;
import com.muscle.trainings.responses.RankingResponse;
import com.muscle.user.entity.IronUser;
import com.muscle.user.service.UserBadgesService;
import com.muscle.user.service.UserService;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointService {
    private final JwtUtil jwtUtil;
    private final PointRepository pointRepository;
    private final UserService userService;
    private final UserBadgesService userBadgesService;

    public void initializePoints(IronUser ironUser) {
        pointRepository.save(Point.builder().user(ironUser).points(0).build());
    }

    public void addPoints(String username, Integer addPoints) {
        Point userPoints = pointRepository.findByUserUsername(username)
                .orElseThrow(() -> new IllegalStateException("Entry in points not found"));
        userPoints.setPoints(userPoints.getPoints() + addPoints);

        pointRepository.save(userPoints);
        userBadgesService.addBadge(username, userPoints.getPoints());
    }

    @Transactional
    public Map<String, Object> getPaginatedRanking(Pageable pageable) {
        Page<Tuple> rankingPage = pointRepository.getRanking(pageable);

        List<RankingResponse> rankingList = rankingPage.getContent().stream()
                .map(tuple -> RankingResponse.builder()
                       .rank(tuple.get(0, BigInteger.class).longValue())
                       .username(tuple.get(1, String.class))
                       .icon(userService.getImage(tuple.get(2, String.class)))
                       .points(tuple.get(3, Integer.class))
                       .build()).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("ranking", rankingList);
        response.put("currentPage", rankingPage.getNumber());
        response.put("totalItems", rankingPage.getTotalElements());
        response.put("totalPages", rankingPage.getTotalPages());

        return response;
    }

    @Transactional
    public RankingResponse getUserRank(String header) {
        Tuple userRank = pointRepository.getRankByUsername(jwtUtil.extractUsername(header)).orElseThrow(()->new IllegalStateException("Ranking entry not found"));

        return RankingResponse.builder()
                .rank(userRank.get(0, BigInteger.class).longValue())
                .username(userRank.get(1, String.class))
                .icon(userService.getImage(userRank.get(2, String.class)))
                .points(userRank.get(3, Integer.class))
                .build();
    }
}
