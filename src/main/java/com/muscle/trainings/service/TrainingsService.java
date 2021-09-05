package com.muscle.trainings.service;

import com.muscle.trainings.dto.ExerciseDto;
import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.entity.Exercise;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.repository.TrainingsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TrainingsService {


    TrainingsRepository trainingsRepository;
    public TrainingDto saveTraining(TrainingDto trainingDto) {
        log.info("Saving new training {} to the database", trainingDto.getName());

        return trainingsRepository.save(Training.builder()
                .name(trainingDto.getName())
                .image(trainingDto.getImage())
                .difficulty(trainingDto.getDifficulty())
                .build())
                .dto();
    }
}
