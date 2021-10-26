package com.muscle.trainings.entity;

import com.muscle.trainings.responses.RankingResponse;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter

@Entity
@Data
@Table(name = "ranking")
public class Ranking {

    @Id
    Long rank;

    String username;
    Integer points;

    public RankingResponse response() {
        return RankingResponse.builder()
                .rank(this.rank)
                .username(this.username)
                .points(this.points)
                .build();
    }
}
