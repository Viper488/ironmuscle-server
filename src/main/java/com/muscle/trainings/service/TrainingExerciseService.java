package com.muscle.trainings.service;

import com.muscle.trainings.dto.AddExerciseRequest;
import com.muscle.trainings.dto.ExerciseDetails;
import com.muscle.trainings.dto.TrainingExerciseDto;
import com.muscle.trainings.dto.TrainingDetails;
import com.muscle.trainings.entity.Exercise;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.entity.TrainingExercise;
import com.muscle.trainings.repository.ExerciseRepository;
import com.muscle.trainings.repository.TrainingExerciseRepository;
import com.muscle.trainings.repository.TrainingsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class TrainingExerciseService {

    TrainingExerciseRepository trainingExerciseRepository;
    TrainingsRepository trainingsRepository;
    ExerciseRepository exerciseRepository;

    public TrainingExerciseDto save(AddExerciseRequest addExerciseRequest) {
        log.info("Saving new training exercise {} to the database", addExerciseRequest.getExerciseId());

        Training training = trainingsRepository.findTrainingById(addExerciseRequest.getTrainingId()).orElseThrow(() -> new IllegalStateException("Training not found!"));
        Exercise exercise = exerciseRepository.findExerciseById(addExerciseRequest.getExerciseId()).orElseThrow(() -> new IllegalStateException("Exercise not found!"));

        return trainingExerciseRepository.save(TrainingExercise.builder()
                .training(training)
                .exercise(exercise)
                .time(addExerciseRequest.getTime())
                .repetitions(addExerciseRequest.getRepetitions())
                .build())
                .dto();
    }

    public TrainingDetails getTrainingDetails(Long trainingId) {
        List<TrainingExercise> trainingExerciseList = trainingExerciseRepository.findAllByTrainingId(trainingId);

        if(trainingExerciseList.isEmpty()) {
            return null;
        }

        return TrainingDetails.builder()
                .training(trainingExerciseList.get(1).getTraining().dto())
                .exercises(trainingExerciseList.stream().map(trainingExercise -> ExerciseDetails.builder()
                        .id(trainingExercise.getExercise().getId())
                        .name(trainingExercise.getExercise().getName())
                        .gif(trainingExercise.getExercise().getGif())
                        .video(trainingExercise.getExercise().getVideo())
                        .time(trainingExercise.getTime())
                        .repetitions(trainingExercise.getRepetitions())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
