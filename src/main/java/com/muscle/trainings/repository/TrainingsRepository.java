package com.muscle.trainings.repository;


import com.muscle.trainings.entity.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingsRepository extends JpaRepository<Training, Long> {

    List<Training> findByType(String type);
    Optional<Training> findTrainingById(Long id);

    void deleteById(@Param("id") Long trainingId);
    //TODO: Delete user training completely

    Page<Training> findByNameContainsOrDifficultyContains(String name, String difficulty, Pageable pageable);

}
