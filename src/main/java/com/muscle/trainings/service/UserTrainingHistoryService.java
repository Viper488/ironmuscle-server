package com.muscle.trainings.service;

import com.muscle.trainings.entity.Training;
import com.muscle.trainings.entity.UserTrainingHistory;
import com.muscle.trainings.repository.TrainingsRepository;
import com.muscle.trainings.repository.UserTrainingHistoryRepository;
import com.muscle.trainings.other.TrainingHistory;
import com.muscle.trainings.responses.UserTrainingHistoryResponse;
import com.muscle.user.entity.IronUser;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.response.IronUserResponse;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TrainingsRepository trainingsRepository;
    private final UserTrainingHistoryRepository userTrainingHistoryRepository;
    private final PointService pointService;

    @Transactional
    public UserTrainingHistoryResponse saveUserActivity(String header, Long trainingId, Integer time) {
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
                .response();
    }

    @Transactional
    public List<TrainingHistory> getUserTrainingHistory(String header, int year, int month) {
        IronUserResponse user = userRepository.findByUsername(jwtUtil.extractUsername(header)).orElseThrow(() -> new IllegalStateException("User not found")).response();
        List<UserTrainingHistory> userTrainingHistory = userTrainingHistoryRepository.findUserHistory(user.getId(), year, month);

        return userTrainingHistory.stream()
                        .map(userTrainingHistoryItem -> TrainingHistory.builder()
                                .id(userTrainingHistoryItem.getId())
                                .name(userTrainingHistoryItem.getTraining().getName())
                                .image(userTrainingHistoryItem.getTraining().getImage())
                                .difficulty(userTrainingHistoryItem.getTraining().getDifficulty())
                                .points(userTrainingHistoryItem.getTraining().getPoints())
                                .date(userTrainingHistoryItem.getTrainingDate())
                                .time(userTrainingHistoryItem.getTrainingTime())
                                .build())
                        .sorted(Comparator.comparing(TrainingHistory::getDate).reversed())
                        .collect(Collectors.toList());
    }
}
