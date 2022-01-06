package com.muscle.trainings.service;

import com.muscle.trainings.dto.TrainingDto;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.mapper.TrainingMapper;
import com.muscle.trainings.other.CreateTrainingDto;
import com.muscle.trainings.repository.TrainingsRepository;
import com.muscle.trainings.responses.TrainingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingsService {

    private final TrainingMapper mapper;
    private final TrainingsRepository trainingsRepository;

    @Transactional
    public Page<Training> getPaginatedTrainings(Pageable pageable, String query) {
        return trainingsRepository.findByNameContainsOrDifficultyContains(query, query, pageable);
    }

    public TrainingResponse saveTraining(CreateTrainingDto trainingDto) {
        log.info("Saving new training {} to the database", trainingDto.getName());
        return trainingsRepository.save(Training.builder()
                .name(trainingDto.getName())
                .type(trainingDto.getType())
                .image("training/" + trainingDto.getImage().toLowerCase() + "_" + trainingDto.getDifficulty().toLowerCase() + ".png")
                .difficulty(trainingDto.getDifficulty())
                .points(trainingDto.getPoints())
                .build())
                .response();
    }

    public TrainingResponse editTraining(TrainingDto trainingDto) {
        log.info("Editing training {} in the database", trainingDto.getName());

        Training trainingEntity = trainingsRepository.findTrainingById(trainingDto.getId()).orElseThrow(() -> new IllegalStateException("Training not found!"));
        mapper.updateTrainingFromDto(trainingDto, trainingEntity);

        return trainingsRepository.save(trainingEntity).response();
    }
}
