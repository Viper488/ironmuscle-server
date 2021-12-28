package com.muscle.trainings.repository;

import com.muscle.trainings.entity.TrainingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRequestRepository extends JpaRepository<TrainingRequest, Long> {
    @Query(value = "SELECT * FROM training_request tr WHERE tr.status = ?1 AND (tr.title LIKE %?2% OR tr.description LIKE %?2%) ORDER BY tr.created_at ASC",
            countQuery = "WITH request_count AS (SELECT * FROM training_request tr WHERE tr.status = ?1 AND (tr.title LIKE %?2% OR tr.description LIKE %?2%) ORDER BY tr.created_at ASC) SELECT COUNT(*) FROM request_count",
            nativeQuery = true)
    Page<TrainingRequest> findByStatusAndQuery(String status, String query, Pageable pageable);

    @Query(value = "SELECT * FROM training_request tr WHERE tr.iron_user_id = ?1 AND tr.status LIKE %?2% AND (tr.title LIKE %?3% OR tr.description LIKE %?3%) ORDER BY tr.created_at DESC",
            countQuery = "WITH user_requests AS (SELECT * FROM training_request tr WHERE tr.iron_user_id = ?1 AND tr.status LIKE %?2% AND (tr.title LIKE %?3% OR tr.description LIKE %?3%) ORDER BY tr.created_at DESC) SELECT COUNT(*) FROM user_requests)",
            nativeQuery = true)
    Page<TrainingRequest> findByUserIdAndStatusAndQuery(Long userId, String status, String query, Pageable pageable);

    @Query(value = "SELECT * FROM training_request tr WHERE tr.trainer_id = ?1 AND tr.status LIKE %?2% AND (tr.title LIKE %?3% OR tr.description LIKE %?3%) ORDER BY tr.created_at DESC",
            countQuery = "WITH trainer_requests AS (SELECT * FROM training_request tr WHERE tr.trainer_id = ?1 AND tr.status LIKE %?2% AND (tr.title LIKE %?3% OR tr.description LIKE %?3%) ORDER BY tr.created_at DESC) SELECT COUNT(*) FROM trainer_requests)",
            nativeQuery = true)
    Page<TrainingRequest> findByTrainerIdAndStatusAndQuery(Long userId, String status, String query, Pageable pageable);

    void deleteById(@Param("id") Long requestId);
    void deleteByUserUsernameAndStatus(String username, String status);
}
