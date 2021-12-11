package com.muscle.trainings.responses;

import com.muscle.user.response.UserSafeDto;
import lombok.*;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingHistoryResponse {
    private Long id;
    private UserSafeDto user;
    private TrainingResponse training;
    private LocalDateTime trainingDate;
    private Integer trainingTime;
}
