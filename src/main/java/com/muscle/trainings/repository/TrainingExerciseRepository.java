package com.muscle.trainings.repository;

import com.muscle.trainings.entity.TrainingExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrainingExerciseRepository extends JpaRepository<TrainingExercise, Long> {

    @Query(value = "SELECT * FROM Training_Exercise te WHERE te.training_id = ?1", nativeQuery = true)
    List<TrainingExercise> findByTrainingId(Long id);

    void deleteByTrainingId(Long id);
}
