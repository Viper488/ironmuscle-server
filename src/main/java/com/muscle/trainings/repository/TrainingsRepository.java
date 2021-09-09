package com.muscle.trainings.repository;


import com.muscle.trainings.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingsRepository extends JpaRepository<Training, Long> {

    List<Training> findAll();
    Optional<Training> findTrainingById(Long id);
}
