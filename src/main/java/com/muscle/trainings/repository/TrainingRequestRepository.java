package com.muscle.trainings.repository;

import com.muscle.trainings.entity.TrainingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRequestRepository extends JpaRepository<TrainingRequest, Long> {
    List<TrainingRequest> findByUserId(Long id);
    List<TrainingRequest> findByTrainerUsernameAndStatus(String username, String status);
    Page<TrainingRequest> findByStatus(String status, Pageable pageable);

    @Query(value = "SELECT * FROM training_request tr WHERE tr.status = ?1 AND (tr.title LIKE %?2% OR tr.description LIKE %?2%)",
            countQuery = "WITH request_count AS (SELECT * FROM training_request tr WHERE tr.status = ?1 AND (tr.title LIKE %?2% OR tr.description LIKE %?2%) SELECT COUNT(*) FROM request_count)",
            nativeQuery = true)
    Page<TrainingRequest> findByStatusTitleDescription(String status, String query, Pageable pageable);


    void deleteById(@Param("id") Long requestId);
    void deleteByUserUsernameAndStatus(String username, String status);
}
