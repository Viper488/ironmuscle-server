package com.muscle.trainings.repository;

import com.muscle.trainings.entity.UserTrainingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTrainingHistoryRepository extends JpaRepository<UserTrainingHistory, Long> {

    List<UserTrainingHistory> findByUserId(Long id);
}
