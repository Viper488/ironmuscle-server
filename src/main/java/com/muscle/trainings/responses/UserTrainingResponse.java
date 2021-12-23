package com.muscle.trainings.responses;

import com.muscle.user.response.UserSafeDto;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingResponse {
    UserSafeDto user;
    TrainingResponse training;
}
