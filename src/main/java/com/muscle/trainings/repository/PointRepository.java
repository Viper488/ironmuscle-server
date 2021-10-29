package com.muscle.trainings.repository;

import com.muscle.trainings.entity.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByUserUsername(String username);

    @Query(value = "SELECT ROW_NUMBER() OVER (ORDER BY p.points DESC), (SELECT username FROM iron_user iu WHERE iu.id = p.user_id), p.points FROM point p",
            countQuery = "WITH ranking_count AS (SELECT ROW_NUMBER() OVER (ORDER BY p.points DESC), (SELECT username FROM iron_user iu WHERE iu.id = p.user_id), p.points FROM point p) SELECT COUNT(*) FROM ranking_count",
            nativeQuery = true)
    Page<Tuple> getRanking(Pageable pageable);

    @Query(value = "WITH ranking AS (SELECT ROW_NUMBER () OVER (ORDER BY p.points DESC), (SELECT username FROM iron_user iu WHERE iu.id = p.user_id), p.points FROM point p) SELECT * FROM ranking r WHERE r.username = ?1", nativeQuery = true)
    Optional<Tuple> getRankByUsername(String username);
}
