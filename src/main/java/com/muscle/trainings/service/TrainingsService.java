package com.muscle.trainings.service;

import com.muscle.trainings.dto.ExerciseDto;
import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.entity.Exercise;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.mapper.TrainingMapper;
import com.muscle.trainings.repository.TrainingsRepository;
import com.muscle.user.repository.UserRepository;
import com.muscle.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingsService {

    private final TrainingMapper mapper;
    private final JwtUtil jwtUtil;
    private final TrainingsRepository trainingsRepository;
    private final UserRepository userRepository;

    public List<TrainingDto> getTemplateTrainings() {
        return trainingsRepository.findByNameStartsWith("Template").stream().map(training -> TrainingDto.builder()
                .id(training.getId())
                .name(training.getName().substring(8))
                .difficulty(training.getDifficulty())
                .image(training.getImage())
                .points(training.getPoints())
                .creator(training.getCreator().dto())
                .build()).collect(Collectors.toList());
    }

    public List<TrainingDto> getTrainings() {
        return trainingsRepository.findAll().stream().map(Training::dto).collect(Collectors.toList());
    }

    public TrainingDto saveTraining(String header, TrainingDto trainingDto) {
        log.info("Saving new training {} to the database", trainingDto.getName());

        return trainingsRepository.save(Training.builder()
                .name(trainingDto.getName())
                .image(trainingDto.getImage())
                .difficulty(trainingDto.getDifficulty())
                .creator(userRepository.findByUsername(jwtUtil.extractUsernameFromHeader(header)).orElseThrow(() -> new IllegalStateException("User not found!")))
                .points(trainingDto.getPoints())
                .build())
                .dto();
    }

    public TrainingDto editTraining(TrainingDto trainingDto) {
        log.info("Editing training {} in the database", trainingDto.getName());

        Training trainingEntity = trainingsRepository.findTrainingById(trainingDto.getId()).orElseThrow(() -> new IllegalStateException("Training not found!"));
        mapper.updateTrainingFromDto(trainingDto, trainingEntity);

        return trainingsRepository.save(trainingEntity).dto();
    }
}
