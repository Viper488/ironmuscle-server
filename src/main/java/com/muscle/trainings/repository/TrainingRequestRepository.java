package com.muscle.trainings.repository;

import com.muscle.trainings.entity.TrainingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRequestRepository extends JpaRepository<TrainingRequest, Long> {
    List<TrainingRequest> findByUserId(Long id);
    List<TrainingRequest> findByEmployeeUsernameAndStatus(String username, String status);
    Page<TrainingRequest> findByStatus(String status, Pageable pageable);
}
