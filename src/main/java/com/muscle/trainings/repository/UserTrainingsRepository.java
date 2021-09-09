package com.muscle.trainings.repository;

import com.muscle.trainings.entity.UserTrainings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTrainingsRepository extends JpaRepository<UserTrainings, Long> {

    @Query(value = "SELECT * FROM User_Trainings ut WHERE ut.user_id = ?1", nativeQuery = true)
    List<UserTrainings> findAllByUserId(Long id);
}
