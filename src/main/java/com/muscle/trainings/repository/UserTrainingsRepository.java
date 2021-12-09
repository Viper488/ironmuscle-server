package com.muscle.trainings.repository;

import com.muscle.trainings.entity.UserTrainings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTrainingsRepository extends JpaRepository<UserTrainings, Long> {
    void deleteByIronUserIdAndTrainingId(Long userId, Long trainingId);
}
