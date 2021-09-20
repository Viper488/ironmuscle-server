package com.muscle.trainings.service;

import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.dto.UserTrainingDto;
import com.muscle.trainings.entity.UserTrainings;
import com.muscle.trainings.repository.TrainingsRepository;
import com.muscle.trainings.repository.UserTrainingsRepository;
import com.muscle.trainings.responses.TrainingResponse;
import com.muscle.trainings.responses.UserTrainingResponse;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserTrainingsService {

    private final UserTrainingsRepository userTrainingsRepository;
    private final UserRepository userRepository;
    private final TrainingsRepository trainingsRepository;
    private final JwtUtil jwtUtil;

    public UserTrainingResponse addTrainingToUser(String header, Long trainingId) {

        String username = jwtUtil.extractUsernameFromHeader(header);
        return userTrainingsRepository.save(UserTrainings.builder()
                .ironUser(userRepository.findByUsername(username)
                        .orElseThrow(() -> new IllegalStateException("User not found")))
                .training(trainingsRepository.findTrainingById(trainingId)
                        .orElseThrow(() -> new IllegalStateException("Training not found")))
                .build()).response();
    }

    public List<TrainingResponse> getUserTrainings(String header) {

        String username = jwtUtil.extractUsernameFromHeader(header);
        List<UserTrainings> userTrainingsList = userTrainingsRepository
                .findAllByUserId(userRepository.findByUsername(username)
                        .orElseThrow(() -> new IllegalStateException("User not found"))
                        .getId());

        if(userTrainingsList.isEmpty()) {
            return new ArrayList<>();
        }

        return userTrainingsList.stream().map(userTrainings -> userTrainings.getTraining().response()).collect(Collectors.toList());
    }
}
