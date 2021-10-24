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
import com.muscle.user.entity.UserBadges;
import com.muscle.user.entity.UserBadgesKey;
import com.muscle.user.repository.BadgeRepository;
import com.muscle.user.repository.UserBadgesRepository;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.response.IronUserResponse;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
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
    private final UserBadgesRepository userBadgesRepository;

    public UserTrainingHistoryResponse saveUserActivity(String header, Long trainingId, Integer time) {
        IronUser user = userRepository.findByUsername(jwtUtil.extractUsername(header))
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Training training = trainingsRepository.findTrainingById(trainingId)
                .orElseThrow(() -> new IllegalStateException("Training not found"));
        List<UserBadges> userBadges = userBadgesRepository.findByUserId(user.getId());
        //List<Badge> userBadgesList = userBadges.stream().map(UserBadges::getBadge).collect(Collectors.toList());
        user.setPoints(user.getPoints() + training.getPoints());

/*        Optional<Badge> lastBadge = Optional.ofNullable(userBadgesList.get(0));
        int biggestGoal = 0;
        if(lastBadge.isPresent()) {
            for (Badge badge : userBadgesList
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
            userBadges.add(UserBadges.builder().id(UserBadgesKey.builder().userId(user.getId()).badgeId(newBadge.getId()).build()).user(user).badge(newBadge).build());
        }*/

        userRepository.save(user);

        return userTrainingHistoryRepository
                .save(UserTrainingHistory.builder()
                        .user(user)
                        .training(training)
                        .trainingDate(LocalDateTime.now())
                        .trainingTime(time)
                        .build())
                .response();
    }

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
