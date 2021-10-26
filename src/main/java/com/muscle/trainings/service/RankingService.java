package com.muscle.trainings.service;

import com.muscle.trainings.entity.Ranking;
import com.muscle.trainings.repository.RankingRepository;
import com.muscle.trainings.responses.RankingResponse;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankingService {
    private final RankingRepository rankingRepository;
    private final JwtUtil jwtUtil;

    public List<RankingResponse> getFirstHundred() {
        return rankingRepository.findFirstHundred().stream().map(Ranking::response).collect(Collectors.toList());
    }

    public RankingResponse getUserRank(String header) {
        return rankingRepository.findByUsername(jwtUtil.extractUsername(header)).orElseThrow(()->new IllegalStateException("Ranking entry not found")).response();
    }
}
