package com.muscle.trainings.repository;

import com.muscle.trainings.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findExerciseById(Long id);
}