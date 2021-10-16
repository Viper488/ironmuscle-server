package com.muscle.trainings.service;

import com.muscle.trainings.entity.Training;
import com.muscle.trainings.entity.UserTrainingHistory;
import com.muscle.trainings.repository.TrainingsRepository;
import com.muscle.trainings.repository.UserTrainingHistoryRepository;
import com.muscle.trainings.other.TrainingHistory;
import com.muscle.trainings.other.UserTrainingHistoryDetails;
import com.muscle.trainings.responses.UserTrainingHistoryResponse;
import com.muscle.user.entity.Badge;
import com.muscle.user.entity.IronUser;
import com.muscle.user.repository.BadgeRepository;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.response.IronUserResponse;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserTrainingHistoryService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TrainingsRepository trainingsRepository;
    private final UserTrainingHistoryRepository userTrainingHistoryRepository;
    private final BadgeRepository badgeRepository;

    public UserTrainingHistoryResponse saveUserActivity(String header, Long trainingId) {
        IronUser user = userRepository.findByUsername(jwtUtil.extractUsername(header))
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Training training = trainingsRepository.findTrainingById(trainingId)
                .orElseThrow(() -> new IllegalStateException("Training not found"));

        user.setPoints(user.getPoints() + training.getPoints());

        Optional<Badge> lastBadge = Optional.ofNullable(user.getBadges().get(0));
        int biggestGoal = 0;
        if(lastBadge.isPresent()) {
            for (Badge badge : user.getBadges()
            ) {
                if (badge.getGoal() > lastBadge.get().getGoal()) {
                    lastBadge = Optional.of(badge);
                }
            }
            biggestGoal = lastBadge.get().getGoal();
        }
        List<Badge> newBadges = badgeRepository.findByGoalBetween(biggestGoal, user.getPoints() + 1);

        for (Badge newBadge:newBadges
            ) {
                user.getBadges().add(newBadge);
        }

        userRepository.save(user);

        return userTrainingHistoryRepository
                .save(UserTrainingHistory.builder()
                        .user(user)
                        .training(training)
                        .trainingDate(LocalDateTime.now())
                        .build())
                .response();
    }

    public UserTrainingHistoryDetails getUserTrainingHistory(String header) {
        IronUserResponse user = userRepository.findByUsername(jwtUtil.extractUsername(header)).orElseThrow(() -> new IllegalStateException("User not found")).response();
        List<UserTrainingHistory> userTrainingHistory = userTrainingHistoryRepository.findByUserId(user.getId());

        return UserTrainingHistoryDetails.builder()
                .user(user)
                .trainingHistory(userTrainingHistory.stream()
                        .map(userTrainingHistoryItem -> TrainingHistory.builder()
                                .id(userTrainingHistoryItem.getTraining().getId())
                                .name(userTrainingHistoryItem.getTraining().getName())
                                .image(userTrainingHistoryItem.getTraining().getImage())
                                .difficulty(userTrainingHistoryItem.getTraining().getDifficulty())
                                .points(userTrainingHistoryItem.getTraining().getPoints())
                                .date(userTrainingHistoryItem.getTrainingDate())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
