package com.muscle.trainings.responses;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RankingResponse {
    Long rank;
    String username;
    Integer points;
}
