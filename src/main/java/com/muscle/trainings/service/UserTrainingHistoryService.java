package com.muscle.trainings.service;

import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.dto.UserActivityRequest;
import com.muscle.trainings.dto.UserTrainingHistoryDto;
import com.muscle.trainings.entity.UserTrainingHistory;
import com.muscle.trainings.repository.TrainingsRepository;
import com.muscle.trainings.repository.UserTrainingHistoryRepository;
import com.muscle.trainings.responses.TrainingHistory;
import com.muscle.trainings.responses.UserTrainingHistoryResponse;
import com.muscle.user.dto.IronUserDto;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public UserTrainingHistoryDto saveUserActivity(String header, UserActivityRequest userActivityRequest) {
        return userTrainingHistoryRepository
                .save(UserTrainingHistory.builder()
                        .user(userRepository.findByUsername(jwtUtil.extractUsernameFromHeader(header))
                                .orElseThrow(() -> new IllegalStateException("User not found")))
                        .training(trainingsRepository.findTrainingById(userActivityRequest.getTrainingId())
                                .orElseThrow(() -> new IllegalStateException("Training not found")))
                        .date(userActivityRequest.getDate())
                        .build())
                .dto();
    }

    public UserTrainingHistoryResponse getUserTrainingHistory(String header) {
        IronUserDto user = userRepository.findByUsername(jwtUtil.extractUsernameFromHeader(header)).orElseThrow(() -> new IllegalStateException("User not found")).dto();
        List<UserTrainingHistory> userTrainingHistory = userTrainingHistoryRepository.findByUserId(user.getId());

        return UserTrainingHistoryResponse.builder()
                .user(user)
                .trainingHistory(userTrainingHistory.stream()
                        .map(userTrainingHistoryItem -> TrainingHistory.builder()
                                .id(userTrainingHistoryItem.getTraining().getId())
                                .name(userTrainingHistoryItem.getTraining().getName())
                                .image(userTrainingHistoryItem.getTraining().getImage())
                                .difficulty(userTrainingHistoryItem.getTraining().getDifficulty())
                                .points(userTrainingHistoryItem.getTraining().getPoints())
                                .date(userTrainingHistoryItem.getDate())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
