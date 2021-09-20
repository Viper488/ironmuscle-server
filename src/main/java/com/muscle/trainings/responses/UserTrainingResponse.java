package com.muscle.trainings.responses;

import com.muscle.user.response.IronUserResponse;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserTrainingResponse {
    Long id;
    IronUserResponse user;
    TrainingResponse training;
}
