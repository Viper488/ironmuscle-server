package com.muscle.trainings.repository;

import com.muscle.trainings.entity.TrainingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRequestRepository extends JpaRepository<TrainingRequest, Long> {
    List<TrainingRequest> findByUserId(Long id);
    List<TrainingRequest> findByEmployeeId(Long id);
    @Query(value = "SELECT employee_id FROM training_request AS tr WHERE tr.employee_id = (SELECT employee_id FROM (SELECT employee_id, COUNT(*) AS requests FROM training_request AS tr2 GROUP BY tr2.employee_id ORDER BY requests ASC LIMIT 1) AS tr3)", nativeQuery = true)
    Optional<Long> selectEmployeeWithLessRequests();
}
