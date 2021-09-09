package com.muscle.trainings.service;

import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.entity.UserTrainings;
import com.muscle.trainings.repository.TrainingsRepository;
import com.muscle.trainings.repository.UserTrainingsRepository;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserTrainingsService {

    private final UserTrainingsRepository userTrainingsRepository;
    private final UserRepository userRepository;
    private final TrainingsRepository trainingsRepository;
    private final JwtUtil jwtUtil;

    public UserTrainings addTrainingToUser(String header, Long trainingId) {

        String username = getUsernameFromHeader(header);
        return userTrainingsRepository.save(UserTrainings.builder()
                .ironUser(userRepository.findByUsername(username)
                        .orElseThrow(() -> new IllegalStateException("User not found")))
                .training(trainingsRepository.findTrainingById(trainingId)
                        .orElseThrow(() -> new IllegalStateException("Training not found")))
                .build());
    }

    public List<TrainingDto> getUserTrainings(String header) {

        String username = getUsernameFromHeader(header);
        List<UserTrainings> userTrainingsList = userTrainingsRepository
                .findAllByUserId(userRepository.findByUsername(username)
                        .orElseThrow(() -> new IllegalStateException("User not found"))
                        .getId());

        if(userTrainingsList.isEmpty()) {
            return new ArrayList<>();
        }

        return userTrainingsList.stream().map(userTrainings -> userTrainings.getTraining().dto()).collect(Collectors.toList());
    }

    private String getUsernameFromHeader(String header) {

        if(header != null && header.startsWith("Bearer ")) {
            String jwt = header.substring(7);

            return jwtUtil.extractUsername(jwt);
        }
        return null;
    }

}
