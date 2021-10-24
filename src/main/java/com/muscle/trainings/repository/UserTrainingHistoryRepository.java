package com.muscle.trainings.repository;

import com.muscle.trainings.entity.UserTrainingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserTrainingHistoryRepository extends JpaRepository<UserTrainingHistory, Long> {

    @Query(value = "SELECT * FROM USER_TRAINING_HISTORY Uth WHERE user_id = ?1 AND EXTRACT(YEAR FROM Uth.training_date) = ?2 AND EXTRACT(MONTH FROM Uth.training_date) = ?3", nativeQuery = true)
    List<UserTrainingHistory> findUserHistory(Long user_id, int year, int month);
}
