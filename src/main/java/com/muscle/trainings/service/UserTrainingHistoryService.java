package com.muscle.trainings.service;

import com.muscle.trainings.dto.UserTrainingHistoryDto;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.entity.UserTrainingHistory;
import com.muscle.trainings.repository.TrainingsRepository;
import com.muscle.trainings.repository.UserTrainingHistoryRepository;
import com.muscle.trainings.other.TrainingHistory;
import com.muscle.user.dto.IronUserDto;
import com.muscle.user.entity.IronUser;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserTrainingHistoryService {

    @Value("${images.main.dir}")
    public String MAIN_DIR;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TrainingsRepository trainingsRepository;
    private final UserTrainingHistoryRepository userTrainingHistoryRepository;
    private final PointService pointService;

    @Transactional
    public UserTrainingHistoryDto saveUserActivity(String header, Long trainingId, Integer time) {
        IronUser user = userRepository.findByUsername(jwtUtil.extractUsername(header))
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Training training = trainingsRepository.findTrainingById(trainingId)
                .orElseThrow(() -> new IllegalStateException("Training not found"));

        pointService.addPoints(user.getUsername(), training.getPoints());

        return userTrainingHistoryRepository
                .save(UserTrainingHistory.builder()
                        .user(user)
                        .training(training)
                        .trainingDate(LocalDateTime.now())
                        .trainingTime(time)
                        .build())
                .dto();
    }

    @Transactional
    public List<TrainingHistory> getUserTrainingHistory(String header, int year, int month) {
        IronUserDto user = userRepository.findByUsername(jwtUtil.extractUsername(header)).orElseThrow(() -> new IllegalStateException("User not found")).dto();
        List<UserTrainingHistory> userTrainingHistory = userTrainingHistoryRepository.findUserHistory(user.getId(), year, month);

        return userTrainingHistory.stream()
                        .map(UserTrainingHistory::response)
                        .sorted(Comparator.comparing(TrainingHistory::getDate).reversed())
                        .collect(Collectors.toList());
    }
}
