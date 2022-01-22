package com.muscle.tests.repository;

import com.muscle.trainings.entity.Exercise;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.entity.TrainingExercise;
import com.muscle.trainings.repository.ExerciseRepository;
import com.muscle.trainings.repository.TrainingExerciseRepository;
import com.muscle.trainings.repository.TrainingsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrainingExerciseRepositoryTests {

    private Long trainingId;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    TrainingsRepository trainingsRepository;


    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    TrainingExerciseRepository trainingExerciseRepository;

    @Before
    public void setupTrainingAndExercises() {
        Training training = Training.builder().name("training_name").type("standard").difficulty("beginner").points(10).build();

        List<Exercise> exercises = new ArrayList<>();
        exercises.add(Exercise.builder()
                        .name("exercise_name_1")
                        .image("image")
                        .video("videoId")
                        .build());
        exercises.add(Exercise.builder()
                .name("exercise_name_2")
                .image("image")
                .video("videoId")
                .build());

        trainingId = (Long) entityManager.persistAndGetId(training);
        exercises.forEach(exercise -> entityManager.persist(exercise));
        exercises.forEach(exercise -> entityManager.persist(TrainingExercise.builder().training(training).exercise(exercise).repetitions(20).time(0).build()));

        entityManager.flush();
    }

    @Test
    public void returnTrainingExercises() {
        List<TrainingExercise> exercises = trainingExerciseRepository.findByTrainingId(trainingId);

        assertEquals(2, exercises.size());
        assertEquals("training_name", exercises.get(0).getTraining().getName());
        assertEquals("training_name", exercises.get(exercises.size() - 1).getTraining().getName());
        assertEquals("exercise_name_1", exercises.get(0).getExercise().getName());
        assertEquals("exercise_name_2", exercises.get(exercises.size() - 1).getExercise().getName());
    }

    @Test
    public void deleteExercisesFromTraining_thenReturnEmptyList() {
        trainingExerciseRepository.deleteByTrainingId(trainingId);

        List<TrainingExercise> exercises = trainingExerciseRepository.findByTrainingId(trainingId);

        assertEquals(0, exercises.size());
    }

}
