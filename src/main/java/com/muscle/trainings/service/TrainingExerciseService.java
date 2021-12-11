package com.muscle.trainings.service;

import com.muscle.trainings.dto.AddExerciseRequest;
import com.muscle.trainings.other.ExerciseResponse;
import com.muscle.trainings.dto.TrainingExerciseDto;
import com.muscle.trainings.other.TrainingDetails;
import com.muscle.trainings.entity.Exercise;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.entity.TrainingExercise;
import com.muscle.trainings.repository.ExerciseRepository;
import com.muscle.trainings.repository.TrainingExerciseRepository;
import com.muscle.trainings.repository.TrainingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingExerciseService {

    private final TrainingExerciseRepository trainingExerciseRepository;
    private final TrainingsRepository trainingsRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public void addExercises(Long trainingId, List<AddExerciseRequest> exercises) {
        exercises.forEach(exercise -> save(trainingId, exercise));
    }

    @Transactional
    public TrainingExerciseDto save(Long trainingId, AddExerciseRequest addExerciseRequest) {
        log.info("Saving new training exercise {} to the database", addExerciseRequest.getExerciseId());

        Training training = trainingsRepository.findTrainingById(trainingId).orElseThrow(() -> new IllegalStateException("Training not found!"));
        Exercise exercise = exerciseRepository.findExerciseById(addExerciseRequest.getExerciseId()).orElseThrow(() -> new IllegalStateException("Exercise not found!"));

        return trainingExerciseRepository.save(TrainingExercise.builder()
                .training(training)
                .exercise(exercise)
                .time(addExerciseRequest.getTime())
                .repetitions(addExerciseRequest.getRepetitions())
                .build())
                .dto();
    }

    @Transactional
    public TrainingDetails getTrainingDetails(Long trainingId) {
        List<TrainingExercise> trainingExerciseList = trainingExerciseRepository.findByTrainingId(trainingId);

        if(trainingExerciseList.isEmpty()) {
            return TrainingDetails.builder().build();
        }

        return TrainingDetails.builder()
                .training(trainingExerciseList.get(1).getTraining().response())
                .exercises(trainingExerciseList.stream().map(trainingExercise -> ExerciseResponse.builder()
                        .id(trainingExercise.getExercise().getId())
                        .name(trainingExercise.getExercise().getName())
                        .image(trainingExercise.getExercise().getImage())
                        .video(trainingExercise.getExercise().getVideo())
                        .time(trainingExercise.getTime())
                        .repetitions(trainingExercise.getRepetitions())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void editExercises(Long trainingId, List<AddExerciseRequest> exercises) {
        trainingExerciseRepository.deleteByTrainingId(trainingId);
        addExercises(trainingId, exercises);
    }
}
