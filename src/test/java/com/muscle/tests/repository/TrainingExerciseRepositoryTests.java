package com.muscle.tests.repository;

import com.muscle.trainings.entity.Exercise;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.entity.TrainingExercise;
import com.muscle.trainings.repository.ExerciseRepository;
import com.muscle.trainings.repository.TrainingExerciseRepository;
import com.muscle.trainings.repository.TrainingsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrainingExerciseRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    TrainingsRepository trainingsRepository;


    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    TrainingExerciseRepository trainingExerciseRepository;

    @Test
    public void addExercisesToTraining_thenReturnTrainingExercises() {
        Training training = Training.builder().name("training_name").type("standard").difficulty("beginner").points(10).build();
        Exercise exercise1 = Exercise.builder().name("exercise_name_1").image("image").video("videoId").build();
        Exercise exercise2 = Exercise.builder().name("exercise_name_2").image("image").video("videoId").build();
        Exercise exercise3 = Exercise.builder().name("exercise_name_3").image("image").video("videoId").build();
        Exercise exercise4 = Exercise.builder().name("exercise_name_4").image("image").video("videoId").build();
        Exercise exercise5 = Exercise.builder().name("exercise_name_5").image("image").video("videoId").build();

        entityManager.persist(training);

        entityManager.persist(exercise1);
        entityManager.persist(exercise2);
        entityManager.persist(exercise3);
        entityManager.persist(exercise4);
        entityManager.persist(exercise5);

        entityManager.persist(TrainingExercise.builder().training(training).exercise(exercise1).repetitions(20).time(0).build());
        entityManager.persist(TrainingExercise.builder().training(training).exercise(exercise2).repetitions(0).time(10).build());
        entityManager.persist(TrainingExercise.builder().training(training).exercise(exercise3).repetitions(40).time(0).build());
        entityManager.persist(TrainingExercise.builder().training(training).exercise(exercise4).repetitions(0).time(30).build());
        entityManager.persist(TrainingExercise.builder().training(training).exercise(exercise5).repetitions(10).time(0).build());

        entityManager.flush();

        List<TrainingExercise> exercises = trainingExerciseRepository.findByTrainingId(training.getId());

        assertEquals(5, exercises.size());
        assertEquals("training_name", exercises.get(0).getTraining().getName());
        assertEquals("training_name", exercises.get(exercises.size() - 1).getTraining().getName());
        assertEquals("exercise_name_1", exercises.get(0).getExercise().getName());
        assertEquals("exercise_name_5", exercises.get(exercises.size() - 1).getExercise().getName());
    }

    @Test
    public void addExercisesToTraining_whenDeleteExercises_thenReturnEmptyList() {
        Training training = Training.builder().name("training_name").type("standard").difficulty("beginner").points(10).build();
        Exercise exercise1 = Exercise.builder().name("exercise_name_1").image("image").video("videoId").build();
        Exercise exercise2 = Exercise.builder().name("exercise_name_2").image("image").video("videoId").build();
        Exercise exercise3 = Exercise.builder().name("exercise_name_3").image("image").video("videoId").build();
        Exercise exercise4 = Exercise.builder().name("exercise_name_4").image("image").video("videoId").build();
        Exercise exercise5 = Exercise.builder().name("exercise_name_5").image("image").video("videoId").build();

        entityManager.persist(training);

        entityManager.persist(exercise1);
        entityManager.persist(exercise2);
        entityManager.persist(exercise3);
        entityManager.persist(exercise4);
        entityManager.persist(exercise5);

        entityManager.persist(TrainingExercise.builder().training(training).exercise(exercise1).repetitions(20).time(0).build());
        entityManager.persist(TrainingExercise.builder().training(training).exercise(exercise2).repetitions(0).time(10).build());
        entityManager.persist(TrainingExercise.builder().training(training).exercise(exercise3).repetitions(40).time(0).build());
        entityManager.persist(TrainingExercise.builder().training(training).exercise(exercise4).repetitions(0).time(30).build());
        entityManager.persist(TrainingExercise.builder().training(training).exercise(exercise5).repetitions(10).time(0).build());

        entityManager.flush();

        trainingExerciseRepository.deleteByTrainingId(training.getId());

        List<TrainingExercise> exercises = trainingExerciseRepository.findByTrainingId(training.getId());

        assertEquals(0, exercises.size());
    }

}
