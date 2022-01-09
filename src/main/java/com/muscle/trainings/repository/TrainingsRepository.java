package com.muscle.trainings.repository;


import com.muscle.trainings.entity.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingsRepository extends JpaRepository<Training, Long> {
    Optional<Training> findTrainingById(Long id);

    void deleteById(@Param("id") Long trainingId);

    Page<Training> findByNameContainsOrDifficultyContains(String name, String difficulty, Pageable pageable);

    @Query(value = "SELECT t.id, t.difficulty, t.image, t.name, t.points, t.type FROM training t LEFT OUTER JOIN user_trainings ut ON ut.training_id = t.id LEFT OUTER JOIN iron_user iu ON iu.id = ut.iron_user_id WHERE (iu.id = ?1 OR t.type = 'standard') AND (t.name LIKE %?2% OR t.difficulty LIKE %?2%) AND (SELECT COUNT(*) FROM training_exercise te WHERE te.training_id = t.id) > 0 ORDER BY t.difficulty, t.name",
            countQuery = "WITH all_training AS (SELECT t.id, t.difficulty, t.image, t.name, t.points, t.type FROM training t LEFT OUTER JOIN user_trainings ut ON ut.training_id = t.id LEFT OUTER JOIN iron_user iu ON iu.id = ut.iron_user_id WHERE (iu.id = ?1 OR t.type = 'standard') AND (t.name LIKE %?2% OR t.difficulty LIKE %?2%) AND (SELECT COUNT(*) FROM training_exercise te WHERE te.training_id = t.id) > 0 ORDER BY t.difficulty, t.name) SELECT COUNT(*) FROM all_training",
            nativeQuery = true)
    Page<Training> findStandardAndCustom(Long userId, String query, Pageable pageable);

    @Query(value = "SELECT t.id, t.difficulty, t.image, t.name, t.points, t.type FROM training t LEFT OUTER JOIN user_trainings ut ON ut.training_id = t.id LEFT OUTER JOIN iron_user iu ON iu.id = ut.iron_user_id WHERE iu.id = ?1 AND (t.name LIKE %?2% OR t.difficulty LIKE %?2%) AND (SELECT COUNT(*) FROM training_exercise te WHERE te.training_id = t.id) > 0 ORDER BY t.difficulty, t.name",
    countQuery = "WITH custom_training AS(SELECT t.id, t.difficulty, t.image, t.name, t.points, t.type FROM training t LEFT OUTER JOIN user_trainings ut ON ut.training_id = t.id LEFT OUTER JOIN iron_user iu ON iu.id = ut.iron_user_id WHERE iu.id = ?1 AND (t.name LIKE %?2% OR t.difficulty LIKE %?2%) AND (SELECT COUNT(*) FROM training_exercise te WHERE te.training_id = t.id) > 0 ORDER BY t.difficulty, t.name) SELECT  COUNT(*) FROM custom_training",
    nativeQuery = true)
    Page<Training> findCustom(Long userId, String query, Pageable pageable);

    @Query(value = "SELECT t.id, t.difficulty, t.image, t.name, t.points, t.type FROM training t LEFT OUTER JOIN user_trainings ut ON ut.training_id = t.id LEFT OUTER JOIN iron_user iu ON iu.id = ut.iron_user_id WHERE t.type = 'standard' AND (t.name LIKE %?1% OR t.difficulty LIKE %?1%) AND (SELECT COUNT(*) FROM training_exercise te WHERE te.training_id = t.id) > 0 ORDER BY t.difficulty, t.name",
            countQuery = "WITH standard_training AS (SELECT t.id, t.difficulty, t.image, t.name, t.points, t.type FROM training t LEFT OUTER JOIN user_trainings ut ON ut.training_id = t.id LEFT OUTER JOIN iron_user iu ON iu.id = ut.iron_user_id WHERE t.type = 'standard' AND (t.name LIKE %?1% OR t.difficulty LIKE %?1%) AND (SELECT COUNT(*) FROM training_exercise te WHERE te.training_id = t.id) > 0 ORDER BY t.difficulty, t.name) SELECT COUNT(*) FROM standard_training",
            nativeQuery = true)
    Page<Training> findStandard(String query, Pageable pageable);

}
