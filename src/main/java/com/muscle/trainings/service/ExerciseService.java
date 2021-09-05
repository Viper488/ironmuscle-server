package com.muscle.trainings.service;

import com.muscle.trainings.dto.ExerciseDto;
import com.muscle.trainings.entity.Exercise;
import com.muscle.trainings.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ExerciseService {

    ExerciseRepository exerciseRepository;
    public ExerciseDto saveExercise(ExerciseDto exerciseDto) {
        log.info("Saving new exercise {} to the database", exerciseDto.getName());

        return exerciseRepository.save(Exercise.builder()
            .name(exerciseDto.getName())
            .gif(exerciseDto.getGif())
            .video(exerciseDto.getVideo())
            .build())
            .dto();
    }
}
