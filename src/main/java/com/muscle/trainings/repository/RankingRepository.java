package com.muscle.trainings.repository;

import com.muscle.trainings.entity.Point;
import com.muscle.trainings.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    @Query(value = "SELECT * FROM RANKING r ORDER BY r.rank ASC LIMIT 100;", nativeQuery = true)
    List<Ranking> findFirstHundred();

    Optional<Ranking> findByUsername(String username);
}
